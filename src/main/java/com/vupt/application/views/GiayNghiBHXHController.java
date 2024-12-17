package com.vupt.application.views;

import com.vupt.application.model.GiayNghiBHXHDetail;
import com.vupt.application.model.GiayNghiBHXHDto;
import com.vupt.application.MyApplication;
import com.vupt.application.utils.FileUtils;
import com.vupt.application.utils.GNBHXHExcelExporter;
import com.vupt.application.utils.GNBHXHExcelImporter;
import com.vupt.application.utils.GNBHXHUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.JMetroStyleClass;
import jfxtras.styles.jmetro.Style;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class GiayNghiBHXHController {
    @Autowired
    ModelMapper modelMapper;
    @FXML
    VBox vBox;
    @FXML
    TextField tfImportFile;
    @FXML
    TextField tfExportFolder;
    @FXML
    DatePicker dpDate;
    @FXML
    TextField tfFilePath;
    @FXML
    Label lbMessage;
    private List<GiayNghiBHXHDetail> giayNghiBHXHDetails;
    private Stage stage;

    public static void loadView(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(GiayNghiBHXHController.class.getResource("/com.vupt.application/views/GiayNghiBHXH.fxml"));
        loader.setControllerFactory(MyApplication.getApplicationContext()::getBean);
        Parent view = loader.load();
        stage.setTitle("Xuất báo cáo giấy nghỉ BHXH");
        Scene scene = new Scene(view);
        stage.setScene(scene);

        JMetro jMetro = new JMetro(Style.LIGHT);
        jMetro.setScene(scene);
        jMetro.getOverridingStylesheets().add(MyApplication.class.getResource("/styles/application.css").toExternalForm());

        GiayNghiBHXHController giayNghiBHXHController = loader.getController();
        giayNghiBHXHController.init(stage);
        stage.show();
    }

    private void init(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void initialize() {
        vBox.getStyleClass().add(JMetroStyleClass.BACKGROUND);
    }

    @FXML
    public void selectImportFile() {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel File(*.xls)", "*.xls"));
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel File(*.xlsx)", "*.xlsx"));
        fc.setTitle("Chọn tệp dữ liệu nhập");
        File file = fc.showOpenDialog(stage);
        if (file == null) {
            System.out.println("No directory selected.");
            return;
        }
        tfImportFile.setText(file.getAbsolutePath());
    }

    @FXML
    public void selectExportFolder() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Chọn thư mục xuất");

        File selectedDirectory = directoryChooser.showDialog(stage);
        if (selectedDirectory == null) {
            System.out.println("No directory selected.");
            return;
        }
        tfExportFolder.setText(selectedDirectory.getAbsolutePath());
    }

    @FXML
    public void importData() throws IOException {
        String importFilePath = tfImportFile.getText().trim();
        String prefix = "file:///";
        if (importFilePath.isEmpty()) {
            lbMessage.setText("Chưa chọn file dữ liệu nhập !");
            return;
        }

        if (importFilePath.startsWith("file:///")) {
            importFilePath = importFilePath.substring(prefix.length());
        }
        this.giayNghiBHXHDetails = GNBHXHExcelImporter.readExcel(importFilePath);
        GNBHXHUtils.sortData(this.giayNghiBHXHDetails);
        lbMessage.setText("Nhập dữ liệu thành công, số lượng = " + this.giayNghiBHXHDetails.size());
    }

    @FXML
    public void exportData() throws IOException {
        String exportPath=GNBHXHUtils.getExportPath(tfExportFolder.getText(),dpDate.getValue());
        List<GiayNghiBHXHDto> giayNghiBHXHDtoList = this.giayNghiBHXHDetails.stream()
                .map(detail -> modelMapper.map(detail, GiayNghiBHXHDto.class))
                .collect(Collectors.toList());
        GNBHXHExcelExporter.writeExcel(giayNghiBHXHDtoList,dpDate.getValue(), exportPath);
        tfFilePath.setText(exportPath);
        FileUtils.openFile(exportPath);
        lbMessage.setText("Xuất dữ liệu thành công");

    }

}
