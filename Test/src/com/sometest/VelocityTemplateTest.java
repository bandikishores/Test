package com.sometest;

import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.jexl2.JexlContext;
import org.apache.commons.jexl2.JexlEngine;
import org.apache.commons.jexl2.MapContext;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.app.event.EventCartridge;
import org.apache.velocity.app.event.ReferenceInsertionEventHandler;
import org.apache.velocity.runtime.resource.loader.StringResourceLoader;
import org.apache.velocity.runtime.resource.util.StringResourceRepository;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

class VelocityTypeHandler implements ReferenceInsertionEventHandler {

    private static final String delimiter = ",";

    @Override
    public Object referenceInsert(String reference, Object value) {
        if (value instanceof Collection<?>) {
            Iterator<?> itr = ((Collection<?>) value).iterator();
            StringBuilder buffer = new StringBuilder();
            while (itr.hasNext()) {
                buffer.append(String.valueOf(handleSpecial(itr.next()).toString()));
                if (itr.hasNext()) buffer.append(delimiter);
            }
            return buffer;
        }
        return handleSpecial(value);
    }

    private Object handleSpecial(Object in) {
        if (in instanceof String) {
            return "'" + in + "'";
        }
        return in;
    }
}



public class VelocityTemplateTest {


    public static class AuditVelocityTools {

        private final JexlEngine jEngine = new JexlEngine();

        @SuppressWarnings({ "rawtypes", "unchecked"})
        public Collection map(Collection src, String objPath) {
            Collection output = new ArrayList();
            JexlContext jCtx = new MapContext();
            for (Object obj : src) {
                String evalExpr = "arg." + objPath;
                org.apache.commons.jexl2.Expression el =
                        (org.apache.commons.jexl2.Expression) jEngine.createExpression(evalExpr);
                jCtx.set("arg", obj);
                output.add(((org.apache.commons.jexl2.Expression) el).evaluate(jCtx));
            }
            return output;
        }

    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Test {
        private int    id;
        private String name;
        private int    value;
        private String emailId;
    }

    @SuppressWarnings("unused")
    public static void main(String[] args) {
        if (false) {
            velocityMerge();
            return;
        }
        if (true) {
            velocityEvaluate();
            return;
        }
    }

    private static void velocityEvaluate() {
        String query =
                "select * from iam_user_view where email_id = ${args[0].emailId} and abc in (${resp}) and id in ( $args[2] ) and email_id in ($auditTool.map($args[1],\"emailId\"))";

        Test test = new Test();
        test.setId(-1);
        test.setEmailId("kishore.bandi@inmobi.com");
        test.setName("Kishore");

        List<Test> list = Arrays.asList(test);

        List<Integer> numList = Arrays.asList(1, 2, 3);

        List<String> strinlist = Arrays.asList("12", "23");

        Object[] args1 = { test, list, numList};
        VelocityContext vCtx = new VelocityContext();
        vCtx.put("args", args1);
        vCtx.put("resp", strinlist);
        vCtx.put("auditTool", new AuditVelocityTools());
        EventCartridge vec = new EventCartridge();
        vec.addEventHandler(new VelocityTypeHandler());
        vec.attachToContext(vCtx);
        Writer out = new StringWriter();

        /*engine.setProperty(Velocity.RESOURCE_LOADER, "string");
        engine.addProperty("string.resource.loader.class", StringResourceLoader.class.getName());
        engine.addProperty("string.resource.loader.repository.static", "false");
        engine.init();*/

        // engine.addProperty("string.resource.loader.class", StringResourceLoader.class.getName());
        new VelocityEngine().evaluate(vCtx, out, "ERR:", new StringReader(query));

        System.out.println(out.toString());
    }

    private static void velocityMerge() {
        // Initialize the engine.
        VelocityEngine engine = new VelocityEngine();
        /*engine.setProperty(RuntimeConstants.RUNTIME_LOG_LOGSYSTEM_CLASS, "org.apache.velocity.runtime.log.Log4JLogChute");
        engine.setProperty("runtime.log.logsystem.log4j.logger", LOGGER.getName());*/
        engine.setProperty(Velocity.RESOURCE_LOADER, "string");
        engine.addProperty("string.resource.loader.class", StringResourceLoader.class.getName());
        engine.addProperty("string.resource.loader.repository.static", "false");
        // engine.addProperty("string.resource.loader.modificationCheckInterval", "1");
        engine.init();

        // Initialize my template repository. You can replace the "Hello $w" with your String.
        StringResourceRepository repo =
                (StringResourceRepository) engine.getApplicationAttribute(StringResourceLoader.REPOSITORY_NAME_DEFAULT);
        repo.putStringResource("woogie2", "Hello $w");

        // Set parameters for my template.
        VelocityContext context = new VelocityContext();
        context.put("w", "world!");

        // Get and merge the template with my parameters.
        Template template = engine.getTemplate("woogie2");
        StringWriter writer = new StringWriter();
        template.merge(context, writer);

        // Show the result.
        System.out.println(writer.toString());
    }
}
