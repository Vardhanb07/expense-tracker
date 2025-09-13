public class Main {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                String url = System.getenv("LOCAL_URI");
                String name = System.getenv("LOCAL_NAME");
                String password = System.getenv("LOCAL_PASS");
                new MainFrame(url, name, password);
            }
        });
    }
}