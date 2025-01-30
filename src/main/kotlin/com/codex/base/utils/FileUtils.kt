import jakarta.enterprise.context.ApplicationScoped
import org.apache.poi.ss.usermodel.*
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.ByteArrayOutputStream
import kotlin.reflect.full.memberProperties

@ApplicationScoped
class FileUtils {

    fun <T : Any> writeExcel(file: String, data: List<T>): ByteArray {
        if (data.isEmpty()) return ByteArray(0) // Return empty byte array if no data

        val workbook = XSSFWorkbook(file)
        val sheet = workbook.createSheet("Sheet1")

        val firstItem = data.first()
        val fields = firstItem::class.memberProperties.toList() // Get all properties

        // Create header row
        val headerRow = sheet.createRow(0)
        fields.forEachIndexed { index, field ->
            headerRow.createCell(index).setCellValue(field.name)
        }

        // Populate data rows
        data.forEachIndexed { rowIndex, item ->
            val row = sheet.createRow(rowIndex + 1) // Offset by 1 to account for header
            fields.forEachIndexed { colIndex, field ->
                val cell = row.createCell(colIndex)
                val value = field.getter.call(item)?.toString() ?: "N/A" // Convert value to string
                cell.setCellValue(value)
            }
        }

        return ByteArrayOutputStream().use { bos ->
            workbook.write(bos)
            bos.toByteArray()
        }
    }
}
