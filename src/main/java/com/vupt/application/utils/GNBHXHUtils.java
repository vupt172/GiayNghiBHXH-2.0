package com.vupt.application.utils;

import com.vupt.application.model.GiayNghiBHXHDetail;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class GNBHXHUtils {
    public static void sortData(List<GiayNghiBHXHDetail> giayNghiBHXHDetails) {
        // Sắp xếp ArrayList theo thuộc tính tên
        Collections.sort(giayNghiBHXHDetails, new Comparator<GiayNghiBHXHDetail>() {
            @Override
            public int compare(GiayNghiBHXHDetail o1, GiayNghiBHXHDetail o2) {
                String hoten1 = o1.getHO_TEN();
                String hoten2 = o2.getHO_TEN();
                String name1 = hoten1.substring(hoten1.lastIndexOf(" "));
                String name2 = hoten2.substring(hoten2.lastIndexOf(" "));
                int compareResult = name1.compareTo(name2);
                if (compareResult == 0) {
                    String last1 = hoten1.substring(0, hoten1.indexOf(" "));
                    String last2 = hoten2.substring(0, hoten2.indexOf(" "));
                    return last1.compareTo(last2);
                } else
                    return compareResult;
            }

        });
        // Đánh lại STT tăng dần
        for(int i=0;i<giayNghiBHXHDetails.size();i++){
            giayNghiBHXHDetails.get(i).setSTT(String.valueOf(i+1));
        }
    }
    public static String getExportPath(String folderPath, LocalDate date){
        return String.format("%s\\Giay Nghi BHXH (%d.%d.%d).xlsx",folderPath,date.getDayOfMonth(),date.getMonthValue(),date.getYear());
    }
    public static String getExcelTitle(LocalDate date){
        return String.format("DUYỆT GIẤY NGHỈ HƯỞNG BHXH NGÀY %d.%d.%d",date.getDayOfMonth(),date.getMonthValue(),date.getYear());
    }
}
