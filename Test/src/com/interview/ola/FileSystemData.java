package com.interview.ola;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
class IntHolder {
	int value;

	public void addSpaces() {
		this.value += Constants.DEFAULT_SPACING;
	}

	public void removeSpaces() {
		this.value -= Constants.DEFAULT_SPACING;
	}
}

@Data
public class FileSystemData {

	Node rootNode = initializeRootNode();

	private Node initializeRootNode() {
		return new Node(Constants.ROOT, FileType.DIRECTORY);
	}

	public Node createNode(String filePath, FileType directory) throws FileSystemException {
		String[] pathSplit = filePath.replaceFirst(Constants.ROOT, "").split(Constants.ROOT);
		Node parentDirectory = null;
		if (pathSplit.length == 1) {
			parentDirectory = rootNode;
		} else {
			parentDirectory = getParentDirectory(pathSplit);
		}

		if (parentDirectory == null) {
			throw new FileSystemException("Could not find the existing path to create directory");
		} else {
			List<Node> childNodes = parentDirectory.getChildNodes();
			if (CollectionUtils.isEmpty(childNodes)) {
				childNodes = new ArrayList<>();
				parentDirectory.setChildNodes(childNodes);
			}
			String directoryName = pathSplit[pathSplit.length - 1];
			Node createdNode = new Node(directoryName, directory);
			childNodes.add(createdNode);
			return createdNode;
		}
	}

	public Optional<Node> getDirectory(String filePath) {
		String[] paths = filePath.replaceFirst(Constants.ROOT, "").split(Constants.ROOT);
		Optional<Node> node = Optional.of(rootNode);
		for (String path : paths) {
			node = fetchDirectory(path, node);
		}
		return node;
	}

	public Optional<Node> getFile(String filePath) {
		String[] completePath = filePath.replaceFirst(Constants.ROOT, "").split(Constants.ROOT);
		Node parentDirectory = getParentDirectory(completePath);
		if (parentDirectory == null) {
			return Optional.empty();
		} else if (CollectionUtils.isNotEmpty(parentDirectory.getChildNodes())) {
			return parentDirectory.getChildNodes().stream()
					.filter(childNode -> childNode.getName().equals(completePath[completePath.length - 1])
							&& childNode.getFileType().equals(FileType.FILE))
					.findFirst();
		}
		return Optional.empty();
	}

	private Optional<Node> fetchDirectory(String path, Optional<Node> optionalNode) {
		if (optionalNode.isPresent()) {
			Node node = optionalNode.get();
			if (node != null && CollectionUtils.isNotEmpty(node.getChildNodes())) {
				return node.getChildNodes().stream().filter(childNode -> childNode.getName().equals(path)
						&& childNode.getFileType().equals(FileType.DIRECTORY)).findFirst();
			}
		}
		return Optional.empty();
	}

	private Node getParentDirectory(String[] completePath) {
		Optional<Node> node = Optional.of(rootNode);
		for (int i = 0; i < completePath.length - 1; i++) {
			String directoryName = completePath[i];
			node = fetchDirectory(directoryName, node);
			if (!node.isPresent()) {
				return null;
			}
		}
		return node.get();
	}

	public String returnNonExistentParentDirectory(String[] completePath) {
		Optional<Node> node = Optional.of(rootNode);
		for (int i = 0; i < completePath.length - 1; i++) {
			String directoryName = completePath[i];
			Optional<Node> optionalNode = fetchDirectory(directoryName, node);
			if (!optionalNode.isPresent()) {
				return directoryName;
			}
		}
		return StringUtils.EMPTY;
	}

	public void deleteNode(String filePath, FileType file) {
		String[] completePath = filePath.replaceFirst(Constants.ROOT, "").split(Constants.ROOT);
		Node parentDirectory = getParentDirectory(completePath);
		if (parentDirectory != null && CollectionUtils.isNotEmpty(parentDirectory.getChildNodes())) {
			for (Iterator<Node> iterator = parentDirectory.getChildNodes().iterator(); iterator.hasNext();) {
				Node node = (Node) iterator.next();
				if (node.getName().equals(completePath[completePath.length - 1]) && node.getFileType().equals(file)) {
					iterator.remove();
					break;
				}
			}
		}
	}

	public String getDirectoryStructureAsString() {
		StringBuilder directoryStructure = new StringBuilder(Constants.ROOT);
		IntHolder totalSpaces = new IntHolder(0);
		addChildDirectory(directoryStructure, totalSpaces, rootNode.getChildNodes());
		return directoryStructure.toString();
	}

	private void addChildDirectory(StringBuilder directoryStructure, IntHolder totalSpaces, List<Node> childNodes) {
		if (CollectionUtils.isNotEmpty(childNodes)) {
			totalSpaces.addSpaces();
			for (Node node : childNodes) {
				directoryStructure.append("\n");
				addSpacesToStructure(directoryStructure, totalSpaces);
				directoryStructure.append(node.getFileType().value + "-" + node.getName());
				addChildDirectory(directoryStructure, totalSpaces, node.getChildNodes());
			}
			totalSpaces.removeSpaces();
		}

	}

	private void addSpacesToStructure(StringBuilder directoryStructure, IntHolder totalSpaces) {
		for (int i = 0; i < totalSpaces.getValue(); i++) {
			directoryStructure.append(" ");
		}
	}

}
