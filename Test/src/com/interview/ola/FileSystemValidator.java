package com.interview.ola;

import java.util.Optional;

import org.apache.commons.lang.StringUtils;

public class FileSystemValidator {

	public void validateCreateDirectory(String filePath, FileSystemData fileSystemData) throws FileSystemException {
		boolean isDirectoryPresent = checkIfDirectoryExists(filePath, fileSystemData);
		if (isDirectoryPresent) {
			throw new FileSystemException("Directory Already Exists");
		}
		String[] completePath = filePath.split(Constants.ROOT);
		String nonExistentDirectory = fileSystemData.returnNonExistentParentDirectory(completePath);
		if (!nonExistentDirectory.equals(StringUtils.EMPTY)) {
			throw new FileSystemException(String.format(
					"Directory %s mentioned does not exist, Please create directory first", nonExistentDirectory));
		}
	}

	public boolean checkIfDirectoryExists(String filePath, FileSystemData fileSystemData) {
		Optional<Node> node = fileSystemData.getDirectory(filePath);
		if (node.isPresent()) {
			return true;
		} else {
			return false;
		}
	}

	public void validateCreateFile(String filePath, FileSystemData fileSystemData) throws FileSystemException {
		boolean isFilePresent = checkIfFileExists(filePath, fileSystemData);
		if (isFilePresent) {
			throw new FileSystemException("File Already Exists");
		}
		String[] completePath = filePath.split(Constants.ROOT);
		String nonExistentDirectory = fileSystemData.returnNonExistentParentDirectory(completePath);
		if (!nonExistentDirectory.equals(StringUtils.EMPTY)) {
			throw new FileSystemException(String.format(
					"Directory %s mentioned does not exist, Please create directory first", nonExistentDirectory));
		}
	}

	private boolean checkIfFileExists(String filePath, FileSystemData fileSystemData) {
		Optional<Node> node = fileSystemData.getFile(filePath);
		if (node.isPresent()) {
			return true;
		} else {
			return false;
		}
	}

	public void validateDeleteFile(String filePath, FileSystemData fileSystemData) throws FileSystemException {
		boolean isFilePresent = checkIfFileExists(filePath, fileSystemData);
		if (!isFilePresent) {
			throw new FileSystemException("File Doesn't Exist");
		}
	}

	public void validateDeleteDirectory(String filePath, FileSystemData fileSystemData) throws FileSystemException {
		boolean isDirectoryPresent = checkIfDirectoryExists(filePath, fileSystemData);
		if (!isDirectoryPresent) {
			throw new FileSystemException("Directory Doesn't Exists");
		}
	}

}
