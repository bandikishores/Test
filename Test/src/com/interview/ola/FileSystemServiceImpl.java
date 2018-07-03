package com.interview.ola;

public class FileSystemServiceImpl {

	FileSystemData fileSystemData = new FileSystemData();

	FileSystemValidator fileSystemValidator = new FileSystemValidator();

	public void createDirectory(String filePath) throws FileSystemException {
		fileSystemValidator.validateCreateDirectory(filePath, fileSystemData);
		fileSystemData.createNode(filePath, FileType.DIRECTORY);
	}

	public void createFile(String filePath, String content) throws FileSystemException {
		fileSystemValidator.validateCreateFile(filePath, fileSystemData);
		Node createNode = fileSystemData.createNode(filePath, FileType.FILE);
		
	}

	public void deleteFile(String filePath) throws FileSystemException {
		fileSystemValidator.validateDeleteFile(filePath, fileSystemData);
		fileSystemData.deleteNode(filePath, FileType.FILE);
	}

	public void deleteDirectory(String filePath) throws FileSystemException {
		fileSystemValidator.validateDeleteDirectory(filePath, fileSystemData);
		fileSystemData.deleteNode(filePath, FileType.DIRECTORY);

	}

	public void listDirectory() {
		String directoryStructure = fileSystemData.getDirectoryStructureAsString();
		System.out.println(directoryStructure);
	}

}
