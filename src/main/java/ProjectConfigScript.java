public class ProjectConfigScript {
    public static void main(String[] args) {
        String setFileLocation = System.getProperty("user.dir");
        System.out.println(setFileLocation);
        setFileLocation = setFileLocation + "\\web\\files";
    }
}
