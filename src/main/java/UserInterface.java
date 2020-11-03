import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.fxmisc.richtext.CodeArea;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class UserInterface extends Application {

    private Stage mainWindow;
    private Scene UIScene;
    private CodeArea Editor;
    private SplitPane SP;
    private MenuBar MB;
    private File OpenedFile;
    private HBox StatusBar;
    private Label caretPosition, fileType;
    private boolean isModified, isFileOpen;
    private String FileContent;
    protected static String CurrentTheme;
    private TreeView<String> FileTree;
    private TreeItem<String> OpenedFileRoot;
    private HashMap<String, String> FileAddresses;
    private ArrayList<String> SupportedFileTypes;

    @Override
    public void start(Stage primaryStage) throws Exception {
        mainWindow = primaryStage;
        FileTree = new TreeView<>();
        FileAddresses = new HashMap<>();
        SupportedFileTypes = new ArrayList<>();
        SupportedFileTypes.add("java");
        SupportedFileTypes.add("cpp");
        SupportedFileTypes.add("c");
        SupportedFileTypes.add("py");
        SupportedFileTypes.add("html");
        SupportedFileTypes.add("css");
        SupportedFileTypes.add("txt");

        FileTree.getSelectionModel().selectedItemProperty().addListener(
            (v, oldVal, newVal) ->
            {
                File Temp;
                try {
                    Temp = new File(FileAddresses.get(newVal.getValue()));    // Calls ReadOpenFile only if tree item is a file.
                    if(Temp.isFile()) {
                        StringBuffer SB = new StringBuffer(Temp.getName());
                        int dotPosition = SB.lastIndexOf(".");
                        String  FileType = SB.substring(dotPosition + 1, SB.length());
                        if(SupportedFileTypes.contains(FileType)) {
                            OpenedFile = Temp;
                            ReadOpenedFile();
                        }
                        else
                            new DialogBox().showAlertBox("Error!", "This file cannot be opened in Ballad.");
                    }
                } catch(NullPointerException NPE){}

            }
        );

//        Font.loadFont(getClass().getResource("Fonts/LiberationMono.ttf").toExternalForm(), 17);
//        Font.loadFont(getClass().getResource("Fonts/Roboto.ttf").toExternalForm(), 15);
//        Font.loadFont(getClass().getResource("Fonts/RobotoBold.ttf").toExternalForm(), 15);
//        EditorRefresh();
//        setEditor();
//        setMenuBar();
//        setStatusBar();

        SP = new SplitPane(FileTree);
        SP.setDividerPositions(0.3);
        SplitPane.setResizableWithParent(FileTree, false);      // Prevents resizing of FileTree with window.

        BorderPane BP = new BorderPane();
        BP.setTop(MB);
        BP.setCenter(SP);
        BP.setBottom(StatusBar);

        UIScene = new Scene(BP, 1280, 720);
//        StartupSettingsLoader();
//        setTheme(CurrentTheme);
        mainWindow.setScene(UIScene);
        mainWindow.show();
//        mainWindow.getIcons().add(new Image(getClass().getResource("Assets/Logo.png").toExternalForm()));
//        mainWindow.show();
//        mainWindow.setOnCloseRequest(this::ExitProcedure);
//        Editor.requestFocus();
    }

    public static void main(String[] args) { launch(args); }

}
