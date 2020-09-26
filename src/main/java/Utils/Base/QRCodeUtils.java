package Utils.Base;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

public class QRCodeUtils {

    private static final String QR_CODE_IMAGE_PATH = "C:/Users/DELL/desktop/MyQRCode.png";


    public static void generateQRCodeImage(String text) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();

        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, 300, 300);

        Path path = FileSystems.getDefault().getPath(QR_CODE_IMAGE_PATH);

        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);

    }
}
