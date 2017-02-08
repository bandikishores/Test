package com;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import com.facebook.ads.sdk.APIContext;
import com.facebook.ads.sdk.APIException;
import com.facebook.ads.sdk.APINode;
import com.facebook.ads.sdk.APINodeList;
import com.facebook.ads.sdk.APIRequest;
import com.facebook.ads.sdk.APIResponse;
import com.facebook.ads.sdk.Ad;
import com.facebook.ads.sdk.Ad.EnumStatus;
import com.facebook.ads.sdk.AdAccount;
import com.facebook.ads.sdk.AdCreative;
import com.facebook.ads.sdk.AdImage;
import com.facebook.ads.sdk.AdSet;
import com.facebook.ads.sdk.BatchRequest;
import com.facebook.ads.sdk.Campaign;
import com.facebook.ads.sdk.Targeting;
import com.facebook.ads.sdk.TargetingGeoLocation;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class FacebookAPITest {

    static File      imageFile = new File("/home/kishore/Pictures/Ads/320x50.jpg");

    static Targeting targeting =
            new Targeting().setFieldGeoLocations(new TargetingGeoLocation().setFieldCountries(Arrays.asList("US")));

    public static void main(String[] args) throws APIException {

        // Long lived Token :
        // https://graph.facebook.com/oauth/access_token?grant_type=fb_exchange_token&client_id=1206643622722606&client_secret=aebe7b8698261d0db63ff299b781ec1f&fb_exchange_token=EAARJb5yAvC4BAPg454ROXrjRVfrIZBSkNjxeOLjQYEpOI1TMtC2POUpBXVIvOZAgfG6z8ZB8FN1M7r4EqfjhzEnFkb55RSveiFRvDpUt80qekyLLxGolQqfzUGxgklWZCYiwu3U20HHyHW8ypw88x3axziMbTN5BQKGbis1HnwZDZD

        /*
         * Short-Lived token can be generated at : 
         * https://developers.facebook.com/tools/explorer/1206643622722606?method=GET&path=me%3Ffields%3Did%2Cname&version=v2.8
         * 
         * App Secret & Client Id can be accessed at :
         * https://developers.facebook.com/apps/1206643622722606/dashboard/
         * 
         * Campaign Management can be done at :
         * https://www.facebook.com/ads/manager/creation/creation/?act=130164813&pid=p1
         * 
         * Format For Long-lived Token:
         * https://graph.facebook.com/oauth/access_token?grant_type=fb_exchange_token&client_id={client_app_id}&client_secret={app_secret}&fb_exchange_token={short_lived_token}
         * 
         */

        Long ACCOUNT_ID = 130164813L;
        String ACCESS_TOKEN =
                "EAARJb5yAvC4BAJcgqJlxpzSbf1h6Bidx0PdbstwwrDnJD0gLHt3wFFLF37JonPkrZAUdGdZC82Uu4Xhhi9yk7yZBPCcJRe8tHtwadxkziQ8MiRLxc9KVf7gGra9lgCXxPexmbFJEnWHLm4SS3AQt5eZAaFDxwKoZD";
        String APP_SECRET = "aebe7b8698261d0db63ff299b781ec1f"; //
        APIContext context = new APIContext(ACCESS_TOKEN, APP_SECRET).enableDebug(true).setLogger(System.out);
        AdAccount account = new AdAccount(ACCOUNT_ID, context);
        Gson gson = new GsonBuilder().create();

        if (true) {
        	
        	
        	/*
GET: https://graph.facebook.com/v2.8/act_130164813/adsetsbylabels?access_token=EAARJb5yAvC4BAJcgqJlxpzSbf1h6Bidx0PdbstwwrDnJD0gLHt3wFFLF37JonPkrZAUdGdZC82Uu4Xhhi9yk7yZBPCcJRe8tHtwadxkziQ8MiRLxc9KVf7gGra9lgCXxPexmbFJEnWHLm4SS3AQt5eZAaFDxwKoZD&appsecret_proof=0775c1b82bc65115265df6e6a71451612d083cdff444edfe312045024a83dd94&ad_label_ids=abcd&fields=account_id%2Cadlabels

        	 */
        	
        	
        	account.getAdSetsByLabels().requestAccountIdField().requestAdlabelsField().setAdLabelIds("abcd").execute();
        	
        	if(true){
        		return;
        	}
        	
            AdCreative creative = account.createAdCreative().setTitle("Java SDK Test Creative")
                    .setBody("Java SDK Test Creative").setImageHash("de63b2e58ce66e4f94038396fb37dda5")
                    // .setLinkUrl("www.facebook.com")
                    .setObjectUrl("www.inmobi.com").execute();
            
            


            APINode ad = account.createAdVideo().setName("Java SDK Test ad")
                    .execute();
            System.out.println("Creation done!");
            
            
            // GET:
            // https://graph.facebook.com/v2.8/act_130164813/ads?access_token=EAARJb5yAvC4BAJcgqJlxpzSbf1h6Bidx0PdbstwwrDnJD0gLHt3wFFLF37JonPkrZAUdGdZC82Uu4Xhhi9yk7yZBPCcJRe8tHtwadxkziQ8MiRLxc9KVf7gGra9lgCXxPexmbFJEnWHLm4SS3AQt5eZAaFDxwKoZD&appsecret_proof=0775c1b82bc65115265df6e6a71451612d083cdff444edfe312045024a83dd94



            return;
        }

        if (false) {
            createCampaign(account);
            return;
        }
        if (false) {
            createAdSet(account, context);
            return;
        }
        if (false) {
            getAdSet(account, context);
            return;
        }
        if(false) {
            getAllAdSet(account, context);
            return;
        }
        if (false) {
            getCampaign(context);
            return;
        }
        if (false) {
            updateCampaign(context);
            return;
        }
        if (false) {
            updateAdSet(account, context);
            return;
        }
        if (false) {
            getAllCampaigns(account);
            return;
        }
        if (false) {
            deleteCampaign(context);
            return;
        }
        if (false) {
            createAndDeleteCampaign(account);
            return;
        }
        if (false) {
            adhocCalls(context);
            return;
        }
        if (false) {
            deleteAllButUpdateCampaign(account, context);
            return;
        }
        if (false) {
            batchInsert(account, context);
            return;
        }
        if (false) {
            createAll(account, context);
            return;
        }
        if (true) {
            createAd(account, context);
            return;
        }
        if (true) {
            copyCampaign(account, context);
            return;
        }
    }



    private static void copyCampaign(AdAccount account, APIContext context) throws APIException {
        Campaign campaign = Campaign.fetchById("6064569565904", context);
        account.createCampaign().setName("Copied Campaign").execute().copyFrom(campaign).update().execute();
    }



    private static void updateAdSet(AdAccount account, APIContext context) throws APIException {
        AdSet adSet = AdSet.fetchById(6065373205304L, context);

        adSet.update().setName("Update_Ad_Set_name1") // set parameter for the API call
                .execute();

        System.out.println(adSet.fetch().getRawResponse());
    }



    private static void getAllAdSet(AdAccount account, APIContext context) throws APIException {
        
        /*
         https://graph.facebook.com/v2.8/6064569565904/adsets?access_token=EAARJb5yAvC4BAJcgqJlxpzSbf1h6Bidx0PdbstwwrDnJD0gLHt3wFFLF37JonPkrZAUdGdZC82Uu4Xhhi9yk7yZBPCcJRe8tHtwadxkziQ8MiRLxc9KVf7gGra9lgCXxPexmbFJEnWHLm4SS3AQt5eZAaFDxwKoZD&appsecret_proof=0775c1b82bc65115265df6e6a71451612d083cdff444edfe312045024a83dd94
         */
        Campaign campaign = Campaign.fetchById("6064569565904", context);
        campaign.getAdSets().execute();
    }

    private static void getAdSet(AdAccount account, APIContext context) throws APIException {
        AdSet adSet = AdSet.fetchById(6065373205304L, context);
        adSet.fetch();
    }



    private static void createAdSet(AdAccount account, APIContext context) throws APIException {
        /*
         Post: https://graph.facebook.com/v2.8/act_130164813/adsets
         Content-Disposition: form-data; name="access_token"
         Content-Disposition: form-data; name="targeting"; value="{"geo_locations":{"countries":["US"]}}"
         Content-Disposition: form-data; name="bid_amount"; value=100
         Content-Disposition: form-data; name="name"; value="Java SDK Test AdSet"
         Content-Disposition: form-data; name="campaign_id"; value="6064750405304"
         ...
         */
        AdSet adset = account.createAdSet().setName("Java SDK Test AdSet").setCampaignId("6064750405304")
                .setBillingEvent(AdSet.EnumBillingEvent.VALUE_IMPRESSIONS).setDailyBudget(100500L).setBidAmount(100L)
                .setTargeting(targeting).execute();

        // Output : {"id":"6064750975504"}
    }



    private static void createAd(AdAccount account, APIContext context) throws APIException {
        // AdImage image = account.createAdImage().addUploadFile("file", imageFile).execute();

        // Output :
        // {"images":{"320x50.jpg":{"hash":"de63b2e58ce66e4f94038396fb37dda5","url":"https:\/\/scontent.xx.fbcdn.net\/t45.1600-4\/15409370_6064692553704_6551403939198140416_n.png"}}}

        AdCreative creative = account.createAdCreative().setTitle("Java SDK Test Creative")
                .setBody("Java SDK Test Creative").setImageHash("de63b2e58ce66e4f94038396fb37dda5")
                // .setLinkUrl("www.facebook.com")
                .setObjectUrl("www.inmobi.com").execute();


        Ad ad = account.createAd().setName("Java SDK Test ad").setAdsetId(6064716077704L).setCreative(creative)
                .setStatus(EnumStatus.VALUE_PAUSED).addUploadFile("myuploadtest", new File("/home/kishore/Pictures/Ads/640*100.jpg")).execute();
        System.out.println("Creation done!");
        System.out.println(ad);

        // Output : {"error":{"message":"Invalid
        // parameter","type":"OAuthException","code":100,"error_data":{"blame_field_specs":[["account_id"]]},"error_subcode":1359101,"is_transient":false,"error_user_title":"Add
        // Payment Method","error_user_msg":"You need to have a valid payment method associated with your ad account
        // before you can create ads.","fbtrace_id":"C0M4tBPmuYc"}}
    }



    private static void createAll(AdAccount account, APIContext context) throws APIException {
        // Creation
        Campaign campaign = account.createCampaign().setName("Java SDK Test Campaign")
                .setObjective(Campaign.EnumObjective.VALUE_EXTERNAL).setStatus(Campaign.EnumStatus.VALUE_PAUSED)
                .execute();
        System.out.println(campaign);
        AdSet adset = account.createAdSet().setName("Java SDK Test AdSet").setCampaignId(campaign.getFieldId())
                .setStatus(AdSet.EnumStatus.VALUE_PAUSED).setBillingEvent(AdSet.EnumBillingEvent.VALUE_IMPRESSIONS)
                .setDailyBudget(100500L).setBidAmount(100L)
                .setOptimizationGoal(AdSet.EnumOptimizationGoal.VALUE_EXTERNAL).setTargeting(targeting)
                .setRedownload(true).execute();
        System.out.println(adset);
        AdImage image = account.createAdImage().addUploadFile("file", imageFile).execute();
        AdCreative creative = account.createAdCreative().setTitle("Java SDK Test Creative")
                .setBody("Java SDK Test Creative").setImageHash(image.getFieldHash())
                // .setLinkUrl("www.facebook.com")
                .setObjectUrl("www.inmobi.com").execute();
        Ad ad = account.createAd().setName("Java SDK Test ad").setAdsetId(Long.parseLong(adset.getId()))
                .setCreative(creative).setStatus("PAUSED").setBidAmount(100L).setRedownload(true).execute();
        System.out.println("Creation done!");

        // Get after creation
        campaign.fetch(); // fetch() is just a shortcut for *.get().requestAllFields().execute()
        adset.fetch();
        ad.fetch();
        System.out.println(campaign);
        System.out.println(adset);
        System.out.println(ad);
        System.out.println("Get after creation done!");

        // call edge to get adsets
        for (AdSet as : campaign.getAdSets().requestAllFields().execute()) {
            for (Ad a : as.getAds().requestAllFields().execute()) {
                System.out.println(a);
            }
            System.out.println(as);
        }
        System.out.println("Get from edge done!");

        // Get with static methods
        System.out.println(Campaign.fetchById(campaign.getFieldId(), context));
        System.out.println(AdSet.fetchById(adset.getFieldId(), context));
        System.out.println("Get with static methods done!");

        // Update
        campaign.update().setName("Updated Java SDK Test Campaign").execute();
        adset.update().setName("Updated Java SDK Test AdSet").execute();
        System.out.println("Update done!");

        // Get after update
        campaign.fetch();
        adset.fetch();
        System.out.println(campaign);
        System.out.println(adset);
        System.out.println("Get after update done!");

        // Delete
        campaign.delete().execute();
        adset.delete().execute();
        System.out.println("Deletion done!");

        // Get after deletion
        campaign.fetch();
        adset.fetch();
        System.out.println(campaign);
        System.out.println(adset);
        for (AdSet as : campaign.getAdSets().requestAllFields().execute()) {
            System.out.println(as);
        }
        System.out.println("Get after deletion done!");
    }



    private static void deleteAllButUpdateCampaign(AdAccount account, APIContext context) throws APIException {
        APINodeList<Campaign> campaigns = account.getCampaigns().requestAllFields().execute();
        for (Campaign campaign : campaigns) {
            campaign = campaign.fetch();
            if (!"Updated Java SDK Test Campaign".equals(campaign.getFieldName())) {
                campaign.delete().execute();
            }
        }
    }



    private static void batchInsert(AdAccount account, APIContext context) throws APIException {

        /*
         Post: https://graph.facebook.com/
         Content-Disposition: form-data; name="access_token"; value="EAARJb5yAvC4BAJcgqJlxpzSbf1h6Bidx0PdbstwwrDnJD0gLHt3wFFLF37JonPkrZAUdGdZC82Uu4Xhhi9yk7yZBPCcJRe8tHtwadxkziQ8MiRLxc9KVf7gGra9lgCXxPexmbFJEnWHLm4SS3AQt5eZAaFDxwKoZD"
         
         Content-Disposition: form-data; name="batch"; value=
                                 [{"method":"POST","relative_url":"v2.8/act_130164813/campaigns","name":"campaignRequest","body":"name=Java+SDK+Batch+Test+Campaign&objective=LINK_CLICKS&status=PAUSED","attached_files":{}},{"method":"POST","relative_url":"v2.8/act_130164813/adsets","name":"adsetRequest","body":"optimization_goal=IMPRESSIONS&targeting=%7B%22geo_locations%22%3A%7B%22countries%22%3A%5B%22US%22%5D%7D%7D&bid_amount=100&billing_event=IMPRESSIONS&name=Java+SDK+Batch+Test+AdSet&daily_budget=1000000&campaign_id=%7Bresult%3DcampaignRequest%3A%24.id%7D&status=PAUSED","attached_files":{}},{"method":"POST","relative_url":"v2.8/act_130164813/adimages","name":"imageRequest","body":"","attached_files":{"File0":"file"}},{"method":"POST","relative_url":"v2.8/act_130164813/adcreatives","name":"creativeRequest","body":"image_hash=%7Bresult%3DimageRequest%3A%24.images.*.hash%7D&link_url=www.facebook.com&object_url=www.facebook.com&title=Java+SDK+Batch+Test+Creative&body=Java+SDK+Batch+Test+Creative","attached_files":{}},{"method":"POST","relative_url":"v2.8/act_130164813/ads","name":"Request4","body":"name=Java+SDK+Batch+Test+ad&adset_id=%7Bresult%3DadsetRequest%3A%24.id%7D&bid_amount=100&creative=%7Bcreative_id%3A%7Bresult%3DcreativeRequest%3A%24.id%7D%7D&status=PAUSED","attached_files":{}}]
                                 
         Content-Disposition: form-data; name="File0"; filename="320x50.jpg"
         Content-Type: image/jpeg
         */

        // Creation of Ad
        BatchRequest batch = new BatchRequest(context);
        account.createCampaign().setName("Java SDK Batch Test Campaign")
                .setObjective(Campaign.EnumObjective.VALUE_LINK_CLICKS).setStatus(Campaign.EnumStatus.VALUE_PAUSED)
                .addToBatch(batch, "campaignRequest");
        account.createAdSet().setName("Java SDK Batch Test AdSet").setCampaignId("{result=campaignRequest:$.id}")
                .setStatus(AdSet.EnumStatus.VALUE_PAUSED).setBillingEvent(AdSet.EnumBillingEvent.VALUE_IMPRESSIONS)
                .setDailyBudget(1000000L).setBidAmount(100L)
                .setOptimizationGoal(AdSet.EnumOptimizationGoal.VALUE_IMPRESSIONS).setTargeting(targeting)
                .addToBatch(batch, "adsetRequest");
        account.createAdImage().addUploadFile("file", imageFile).addToBatch(batch, "imageRequest");
        account.createAdCreative().setTitle("Java SDK Batch Test Creative").setBody("Java SDK Batch Test Creative")
                .setImageHash("{result=imageRequest:$.images.*.hash}").setLinkUrl("www.facebook.com")
                .setObjectUrl("www.facebook.com").addToBatch(batch, "creativeRequest");
        account.createAd().setName("Java SDK Batch Test ad").setAdsetId("{result=adsetRequest:$.id}")
                .setCreative("{creative_id:{result=creativeRequest:$.id}}").setStatus("PAUSED").setBidAmount(100L)
                .addToBatch(batch);

        List<APIResponse> responses = batch.execute();

        System.out.println(responses);

        // Obtain the IDs of the newly created objects
        Ad ad = ((Ad) responses.get(4)).fetch();
        AdSet adSet = new AdSet(ad.getFieldAdsetId(), context).fetch();
        Campaign campaign = new Campaign(adSet.getFieldCampaignId(), context);

        // Load
        batch = new BatchRequest(context);
        ad.get().requestAllFields().addToBatch(batch);
        adSet.get().requestAllFields().addToBatch(batch);
        campaign.get().requestAllFields().addToBatch(batch);
        responses = batch.execute();

        System.out.println((Ad) responses.get(0));
        System.out.println((AdSet) responses.get(1));
        System.out.println((Campaign) responses.get(2));

        // Delete
        batch = new BatchRequest(context);
        // ad.delete().addToBatch(batch); // Deleting campaign automatically deletes ad and adset.
        // adSet.delete().addToBatch(batch); // Deleting campaign automatically deletes ad and adset.
        campaign.delete().addToBatch(batch);
        responses = batch.execute();
        System.out.println(responses.get(0));

    }



    private static void adhocCalls(APIContext context) throws APIException {
        APIRequest<AdAccount> request =
                new APIRequest<AdAccount>(context, "me", "/adaccounts", "GET", AdAccount.getParser());
        APINodeList<AdAccount> accounts = (APINodeList<AdAccount>) (request.execute());
        System.out.println(accounts.getRawResponse());
    }



    private static void createAndDeleteCampaign(AdAccount account) throws APIException {
        Campaign campaign = account.createCampaign().setName("Java SDK Test Campaign 1")
                .setObjective(Campaign.EnumObjective.VALUE_LINK_CLICKS).execute();

        System.out.println(campaign.fetch());

        APINode apiNode = campaign.delete().execute();
        System.out.println(apiNode.getRawResponse());
    }

    private static void deleteCampaign(APIContext context) throws APIException {
        Campaign campaign = Campaign.fetchById("6064690780304", context);
        APINode apiNode = campaign.delete().execute();
        System.out.println(apiNode.getRawResponse());
    }

    private static void getAllCampaigns(AdAccount account) throws APIException {
        
        // https://graph.facebook.com/v2.8/act_130164813/campaigns?access_token=EAARJb5yAvC4BAJcgqJlxpzSbf1h6Bidx0PdbstwwrDnJD0gLHt3wFFLF37JonPkrZAUdGdZC82Uu4Xhhi9yk7yZBPCcJRe8tHtwadxkziQ8MiRLxc9KVf7gGra9lgCXxPexmbFJEnWHLm4SS3AQt5eZAaFDxwKoZD&appsecret_proof=0775c1b82bc65115265df6e6a71451612d083cdff444edfe312045024a83dd94&fields=account_id%2Cadlabels%2Cbudget_rebalance_flag%2Cbuying_type%2Ccan_use_spend_cap%2Cconfigured_status%2Ccreated_time%2Ceffective_status%2Cid%2Cname%2Cobjective%2Crecommendations%2Cspend_cap%2Cstart_time%2Cstatus%2Cstop_time%2Cupdated_time
        
        APINodeList<Campaign> campaigns = account.getCampaigns().requestAllFields().execute();
        for (Campaign campaign : campaigns) {
            System.out.println(campaign.fetch().getRawResponse());
        }

        // Output :
        // {"account_id":"130164813","budget_rebalance_flag":false,"buying_type":"AUCTION","can_use_spend_cap":true,"configured_status":"PAUSED","created_time":"2016-12-06T02:57:19-0800","effective_status":"PAUSED","id":"6064569565904","name":"Updated
        // Java SDK Test
        // Campaign","objective":"LINK_CLICKS","spend_cap":"10000","start_time":"1969-12-31T15:59:59-0800","status":"PAUSED","updated_time":"2016-12-08T00:09:27-0800"}

    }

    private static void updateCampaign(APIContext context) throws APIException {
        Campaign campaign = Campaign.fetchById("6064569565904", context);

        campaign.update().setName("Updated Java SDK Test Campaign1") // set parameter for the API call
                .execute();

        System.out.println(campaign.fetch().getRawResponse());
        // Output :
        // {"account_id":"130164813","budget_rebalance_flag":false,"buying_type":"AUCTION","can_use_spend_cap":true,"configured_status":"PAUSED","created_time":"2016-12-06T02:57:19-0800","effective_status":"PAUSED","id":"6064569565904","name":"Updated
        // Java SDK Test
        // Campaign","objective":"LINK_CLICKS","spend_cap":"10000","start_time":"1969-12-31T15:59:59-0800","status":"PAUSED","updated_time":"2016-12-08T00:09:27-0800"}
    }

    private static void getCampaign(APIContext context) throws APIException {
        /*
         
        APINodeList<Campaign> campaignsList = account.getCampaigns().requestAllFields().execute();
        System.out.println(campaignsList.get(0).fetch().getRawResponse());
          
          OR
         */
        
        // https://graph.facebook.com/v2.8/6064569565904/?access_token=EAARJb5yAvC4BAJcgqJlxpzSbf1h6Bidx0PdbstwwrDnJD0gLHt3wFFLF37JonPkrZAUdGdZC82Uu4Xhhi9yk7yZBPCcJRe8tHtwadxkziQ8MiRLxc9KVf7gGra9lgCXxPexmbFJEnWHLm4SS3AQt5eZAaFDxwKoZD&appsecret_proof=0775c1b82bc65115265df6e6a71451612d083cdff444edfe312045024a83dd94&fields=account_id%2Cadlabels%2Cbudget_rebalance_flag%2Cbuying_type%2Ccan_use_spend_cap%2Cconfigured_status%2Ccreated_time%2Ceffective_status%2Cid%2Cname%2Cobjective%2Crecommendations%2Cspend_cap%2Cstart_time%2Cstatus%2Cstop_time%2Cupdated_time
        
        Campaign campaign = Campaign.fetchById("6064569565904", context);
        System.out.println(campaign.fetch().getRawResponse());

        // Output :
        // {"account_id":"130164813","budget_rebalance_flag":false,"buying_type":"AUCTION","can_use_spend_cap":true,"configured_status":"PAUSED","created_time":"2016-12-06T02:57:19-0800","effective_status":"PAUSED","id":"6064569565904","name":"Java
        // SDK Test
        // Campaign","objective":"LINK_CLICKS","spend_cap":"10000","start_time":"1969-12-31T15:59:59-0800","status":"PAUSED","updated_time":"2016-12-06T02:57:19-0800"}
    }

    private static void createCampaign(AdAccount account) throws APIException {
        Campaign campaign = account.createCampaign().setName("Java SDK Test Campaign")
                .setObjective(Campaign.EnumObjective.VALUE_LINK_CLICKS).execute();

        System.out.println(campaign.fetch());

        // Output :
        // {"account_id":"130164813","budget_rebalance_flag":false,"buying_type":"AUCTION","can_use_spend_cap":true,"configured_status":"PAUSED","created_time":"2016-12-06T02:57:19-0800","effective_status":"PAUSED","id":"6064569565904","name":"Java
        // SDK Test
        // Campaign","objective":"LINK_CLICKS","spend_cap":"10000","start_time":"1969-12-31T15:59:59-0800","status":"PAUSED","updated_time":"2016-12-06T02:57:19-0800"}



        return;
    }

}
