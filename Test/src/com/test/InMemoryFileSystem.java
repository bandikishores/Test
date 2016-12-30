package com.test;

import java.util.Scanner;

public class InMemoryFileSystem {

	FileSystemServiceImpl fileSystemService = new FileSystemServiceImpl();

	public static void main(String[] args) {
		new InMemoryFileSystem().startInMemoryFileSystem();
	}

	private void startInMemoryFileSystem() {
		try (Scanner sc = new Scanner(System.in)) {
			while (true) {
				displayMenuScreen();

				String userSelection = sc.nextLine();

				if (!org.apache.commons.lang.StringUtils.isNumeric(userSelection)) {
					System.out.println("Please enter an integer");
				} else {
					try {
						String fileName;
						switch (userSelection) {
						case "1":
							fileName = acceptFileName(sc);
							fileSystemService.createDirectory(fileName);
							System.out.println(String.format("Directory %s created succesfully", fileName));
							break;
						case "2":
							fileName = acceptFileName(sc);
							String fileContent = sc.nextLine();
							fileSystemService.createFile(fileName, fileContent);
							System.out.println(String.format("File %s created succesfully", fileName));
							break;
						case "3":
							fileName = acceptFileName(sc);
							fileSystemService.deleteFile(fileName);
							System.out.println(String.format("File %s deleted succesfully", fileName));
							break;
						case "4":
							fileName = acceptFileName(sc);
							fileSystemService.deleteDirectory(fileName);
							System.out.println(String.format("Directory %s deleted succesfully", fileName));
							break;
						case "5":
							fileSystemService.listDirectory();
							break;
						case "6":
							return;
						default:
							System.out.println("Invalid Selection");
						}
					} catch (FileSystemException ex) {
						System.out.println(ex.getMessage());
					} catch (Exception ex) {
						System.out.println("Internal Error occured!! Please Try Again");
					}
				}
			}
		}
	}

	private String acceptFileName(Scanner sc) throws FileSystemException {
		System.out.println("Please enter the Name with fully qualified Path : ");
		String fileName = sc.nextLine();

		if (fileName == null || fileName.trim().length() == 0) {
			throw new FileSystemException("Name cannot be empty or blank or just white spaces");
		} else if (fileName.equals(Constants.ROOT) || fileName.endsWith(Constants.ROOT) || !fileName.startsWith(Constants.ROOT)) {
			throw new FileSystemException("Name cannot be ROOT, cannot end with ROOT, must begin with ROOT");
		}

		return fileName;
	}

	private void displayMenuScreen() {
		System.out.println("1 - Create Directory ");
		System.out.println("2 - Create File ");
		System.out.println("3 - Delete File ");
		System.out.println("4 - Delete Directory ");
		System.out.println("5 - List Directory ");
		System.out.println("6 - Exit ");
		System.out.println("Please enter your choice with the Integer Value : ");
	}

}
