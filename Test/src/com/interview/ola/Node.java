package com.interview.ola;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@EqualsAndHashCode(of = { "name", "fileType" })
public class Node {

	final String name;

	final FileType fileType;
	
	String fileContent;

	List<Node> childNodes;
}
