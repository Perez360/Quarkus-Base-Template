package com.codex.base.utils

import jakarta.enterprise.context.ApplicationScoped
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.ByteArrayOutputStream


@ApplicationScoped
class FileUtils {

    fun <T> writeExcel(fileName: String, data: List<T>): ByteArray {
        val workbook = XSSFWorkbook()
        val sheet = workbook.createSheet("Sheet1")
        data.forEachIndexed { index, t ->
            val row = sheet.createRow(index)
            val cell = row.getCell(0)
            cell.setCellValue(t as String)
        }

        val bos = ByteArrayOutputStream()
        bos.use { workbook.write(it) }

        return bos.toByteArray()
    }
}