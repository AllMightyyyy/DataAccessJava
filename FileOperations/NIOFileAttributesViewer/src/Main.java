import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.*;
import java.util.List;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        /*
        This program retrieves and displays detailed file attributes using NIO.
        It uses `Files` and `BasicFileAttributes` to display creation time, last modified time, and file size.
        It also displays owner, permissions, and access control lists (ACLs) if supported by the OS.
         */

        String filePath = "FileToManipulate"; 
        Path path = Path.of(filePath);

        try {
            // Retrieve basic file attributes
            BasicFileAttributes attributes = Files.readAttributes(path, BasicFileAttributes.class);
            System.out.println("Creation Time -> " + attributes.creationTime());
            System.out.println("Last Access Time -> " + attributes.lastAccessTime());
            System.out.println("Last Modified Time -> " + attributes.lastModifiedTime());
            System.out.println("Is Directory -> " + attributes.isDirectory());
            System.out.println("Is Regular File -> " + attributes.isRegularFile());
            System.out.println("Is Symbolic Link -> " + attributes.isSymbolicLink());
            System.out.println("Is Other -> " + attributes.isOther());
            System.out.println("File Size (bytes) -> " + attributes.size());

            // Retrieve file owner
            UserPrincipal owner = Files.getOwner(path);
            System.out.println("File Owner -> " + owner.getName());

            // Retrieve file permissions (POSIX-compliant file systems only)
            if (FileSystems.getDefault().supportedFileAttributeViews().contains("posix")) {
                Set<PosixFilePermission> permissions = Files.getPosixFilePermissions(path);
                System.out.println("File Permissions -> " + PosixFilePermissions.toString(permissions));
            } else {
                System.out.println("File Permissions -> Not supported on this file system.");
            }

            // Retrieve Access Control Lists (ACLs)
            if (Files.getFileAttributeView(path, AclFileAttributeView.class) != null) {
                AclFileAttributeView aclView = Files.getFileAttributeView(path, AclFileAttributeView.class);
                List<AclEntry> aclList = aclView.getAcl();
                System.out.println("Access Control List (ACL):");
                for (AclEntry entry : aclList) {
                    System.out.println(entry);
                }
            } else {
                System.out.println("Access Control List (ACL) -> Not supported on this file system.");
            }

        } catch (IOException e) {
            System.err.println("An error occurred while retrieving file attributes: " + e.getMessage());
        }
    }
}
