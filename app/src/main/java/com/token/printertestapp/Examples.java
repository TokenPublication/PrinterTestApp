package com.token.printertestapp;

import android.content.Context;
import android.util.Log;
import android.util.Printer;
import android.widget.TextView;
import android.widget.Toast;

import com.token.printerlib.PrinterDefinitions;
import com.token.printerlib.PrinterDefinitions.Font_E;
import com.token.printerlib.PrinterDefinitions.Alignment;
import com.token.printerlib.PrinterService;
import com.token.printerlib.StyledString;

public class Examples {


    public static final int bottomMargin = 120; // margin that is left after each print
    private static final String TAG = "Printer Service Examples";

    static void TestFonts() {
        PrinterService.getService( printerService -> {

            if (printerService == null) {
                Log.d(TAG, "Printer Service was null");
                return;
            }

            for (Font_E font : Font_E.values()) {
                printerService.setFontFace(font.ordinal());
                for (int fontSize : PrinterDefinitions.fontSizes) {
                    if (fontSize > 20)
                        continue;

                    printerService.setFontSize(fontSize);
                    printerService.addTextToLine(font.toString() + " " + fontSize + "pt ", PrinterDefinitions.Alignment.Left.ordinal());
                    printerService.printLine();
                    printerService.addTextToLine("SOME TEXT String 08", Alignment.Left.ordinal());
                    printerService.printLine();
                }

            }
            printerService.addSpace(bottomMargin);
        });
    }

    static void PrintLoremIpsum(boolean uppercase)
    {
        PrinterService.getService( ps -> {

            if (ps == null) {
                Log.d(TAG, "Printer Service was null");
                return;
            }

            String loremIpsumStr = "Lorem ipsum dolor sit amet,\nconsectetur adipiscing elit.\nProin sollicitudin a dolor sit amet porttitor.\nCras sed felis at erat tempus\nmalesuada accumsan vel augue.\nNunc sed lorem elementum,\npretium metus sit amet, ornare urna.\nProin a massa a arcu placerat lobortis.\nPraesent mattis, lacus a consequat pellentesque,\narcu dolor fringilla lorem,\nat malesuada nunc arcu vel felis.\nAliquam facilisis erat tempor diam maximus euismod a a arcu. ";
            if (uppercase)
                loremIpsumStr = loremIpsumStr.toUpperCase();
            ps.setLineSpacing(1.0f);
            ps.printText(loremIpsumStr);
            ps.addSpace(bottomMargin);
        });
    }

    static void PrintAllCharacters(boolean includeTurkishCharacters)
    {
        PrinterService.getService( ps -> {

            if (ps == null) {
                Log.d(TAG, "Printer Service was null");
                return;
            }

            String text = "";

            for (int i = 32; i < 128; i++) {
                text += (char) i;
                if (i % 16 == 0)
                    text += '\n';
            }

            if (includeTurkishCharacters)
                text += "\nçÇğĞıİöÖşŞüÜ" + "\u20BA"; // turkish characters and turkish lira symbol

            ps.printText(text);
            ps.addSpace(bottomMargin);
        });
    }

    static void PrintSampleReceipt() {
        final int lineThickness = 2;

        PrinterService.getService(ps -> {

            if (ps == null) {
                Log.d(TAG, "Printer Service was null");
                return;
            }

            ps.setFontFace(Font_E.Sans_Semi_Bold.ordinal());
            ps.setFontSize(16);
            ps.setLineSpacing(1.4f);
            ps.printText("Sales Report");

            ps.setFontFace(Font_E.SourceSansPro.ordinal());
            ps.setFontSize(14);
            ps.setLineSpacing(1.1f);
            ps.printText("February 1, 2019 12::00AM-\n"
                    + "February 1, 2019 12::00AM");

            ps.setFontFace(Font_E.Sans_Semi_Bold.ordinal());
            ps.drawLine(lineThickness, 10, 0);

            ps.setLineSpacing(1.0f);
            ps.printText("SALES SUMMARY");
            ps.drawLine(lineThickness, 10, 0);

            ps.setLineSpacing(1.3f);
            ps.printText("Gross Sales\t\t 14.00 ₺");

            ps.setFontFace(Font_E.SourceSansPro.ordinal());

            ps.printText("Refunds\t\t\t 0.00 ₺\n"
                    + "Discounts\t\t\t 0.00 ₺");

            ps.setFontFace(Font_E.Sans_Semi_Bold.ordinal());
            ps.printText("Net Sales\t\t\t 14.00 ₺");
            ps.setFontFace(Font_E.SourceSansPro.ordinal());
            ps.printText("Tax\t\t\t\t $0.00\n"
                    + "Tip\t\t\t\t $0.00\n"
                    + "Gift Card Sales\t\t 0.00 ₺\n"
                    + "Partial Refunds\t\t 0.00 ₺");

            ps.setFontFace(Font_E.Sans_Semi_Bold.ordinal());
            ps.printText("Total Collected\t\t 0.00 ₺");
            ps.setFontFace(Font_E.SourceSansPro.ordinal());
            ps.printText("Cash\t\t\t\t 14.00 ₺\n"
                    + "Card\t\t\t\t 0.00 ₺\n"
                    + "Gift Card\t\t\t 0.00 ₺\n"
                    + "Other\t\t\t 0.00 ₺\n"
                    + "Fees\t\t\t\t 0.00 ₺");
            ps.setFontFace(Font_E.Sans_Semi_Bold.ordinal());
            ps.printText("Net Total\t\t\t 14.00 ₺");

            ps.drawLine(lineThickness, 10, 0);
            ps.setLineSpacing(1.0f);
            ps.printText("CATEGORY SALES");
            ps.drawLine(lineThickness, 10, 0);
            ps.setLineSpacing(1.3f);

            ps.setFontFace(Font_E.SourceSansPro.ordinal());
            ps.printText("Drinks x 1\t\t\t 2.00 ₺\n"
                    + "Maels x 1\t\t\t 2.00 ₺");

            ps.setFontFace(Font_E.Sans_Semi_Bold.ordinal());
            ps.drawLine(lineThickness, 10, 0);
            ps.setLineSpacing(1.0f);
            ps.printText("ITEM SALES");
            ps.drawLine(lineThickness, 10, 0);
            ps.setLineSpacing(1.3f);

            ps.setFontFace(Font_E.SourceSansPro.ordinal());
            ps.printText("Icetea(Regular)x1\t 2.00 ₺\n"
                    + "Pizza(Regular)x1\t 2.00 ₺");

            ps.addSpace(bottomMargin);
        });
    }

    static void PrintSampleReceipt2()
    {
        StyledString styledText = new StyledString();

        styledText.printBitmap("gib");
        styledText.addSpace(20);

        styledText.setLineSpacing(1.0f);
        styledText.setFontFace(Font_E.Sans_Semi_Bold);
        styledText.setFontSize(16);
        styledText.addTextToLine("ACME INC.", Alignment.Center);
        styledText.newLine(); // equivalent to styledText.addText("\n");

        styledText.setFontFace(Font_E.SourceSansPro);
        styledText.setFontSize(12);
        styledText.addTextToLine("Afiyet Olsun", Alignment.Center);
        styledText.newLine();

        styledText.addTextToLine("AT0000150552", Alignment.Center);
        styledText.newLine();

        styledText.addTextToLine("12-03-2019");
        styledText.addTextToLine("FİŞ NO: 12", Alignment.Right);
        styledText.newLine();

        styledText.addText("SAAT: 15:15\n");

        styledText.addSpace(10);

        styledText.addTextToLine("YİYECEK");
        styledText.addTextToLine("%8", Alignment.Center);
        styledText.addTextToLine("150,00 ₺", Alignment.Right);
        styledText.newLine();

        styledText.addTextToLine("İÇECEK");
        styledText.addTextToLine("%18", Alignment.Center);
        styledText.addTextToLine("48,15 ₺", Alignment.Right);
        styledText.newLine();

        styledText.addTextToLine("YİYECEK");
        styledText.addTextToLine("%8", Alignment.Center);
        styledText.addTextToLine("150,00 ₺", Alignment.Right);
        styledText.newLine();

        styledText.addTextToLine("YİYECEK");
        styledText.addTextToLine("%8", Alignment.Center);
        styledText.addTextToLine("150,00 ₺", Alignment.Right);
        styledText.newLine();

        styledText.addTextToLine("İÇECEK");
        styledText.addTextToLine("%18", Alignment.Center);
        styledText.addTextToLine("10,00 ₺", Alignment.Right);
        styledText.newLine();

        styledText.addTextToLine("YİYECEK");
        styledText.addTextToLine("%8", Alignment.Center);
        styledText.addTextToLine("150,00 ₺", Alignment.Right);
        styledText.newLine();

        styledText.addSpace(10);

        styledText.setFontFace(Font_E.Sans_Semi_Bold);
        styledText.setFontSize(14);
        styledText.addTextToLine("TOPKDV");
        styledText.addTextToLine("35,51 ₺", Alignment.Right);
        styledText.newLine();

        styledText.addTextToLine("TOPLAM");
        styledText.addTextToLine("305,51 ₺", Alignment.Right);
        styledText.newLine();

        styledText.addSpace(10);

        styledText.setFontFace(Font_E.SourceSansPro);
        styledText.setFontSize(12);
        styledText.addTextToLine("NAKİT");
        styledText.addTextToLine("305,51 ₺", Alignment.Right);
        styledText.newLine();

        styledText.addSpace(10);

        styledText.addTextToLine("EKÜ NO: 1");
        styledText.addTextToLine("Z NO: 258", Alignment.Right);
        styledText.newLine();

        styledText.addSpace(10);

        styledText.addTextToLine("uF AT 0000150258", Alignment.Center);
        styledText.newLine();


        // you can print predefined bitmap files just by its name, optionally with a vertical margin
        //styledText.addSpace(10);
        //styledText.printBitmap("ykb, 20");


        styledText.printBitmap("ykb");

        // you can add multiline text
        //styledText.addText("this is a multiline\ntext that can have \t\t tabs");

        styledText.addSpace(bottomMargin);

        styledText.print(PrinterService.getService());
    }

    static void changingSizesTest()
    {
        PrinterService.getService(ps -> {

            if (ps == null) {
                Log.d(TAG, "Printer Service was null");
                return;
            }

            ps.setFontSize(12);
            ps.addTextToLine("MIDDLE ", Alignment.Left.ordinal());
            ps.setFontSize(8);
            ps.addTextToLine("SMALL ", Alignment.Left.ordinal());
            ps.setFontSize(16);
            ps.addTextToLine("LARGE ", Alignment.Left.ordinal());
            ps.printLine();
            ps.addSpace(bottomMargin);
        });
    }

    static void _48LettersTest()
    {
        final String _48CharsString = "ABCÇDEFGĞHIİJKLMNOÖPRSŞTUÜVYZ 123456789012345678";
        StyledString styledText = new StyledString();
        styledText.setLineSpacing(0.5f);
        styledText.setFontFace(Font_E.basicFont_8x8);
        styledText.addTextToLine("BASICFONT 8x8, MAX 48 KARAKTER");
        styledText.endLine();
        styledText.addTextToLine("MONO SPACE");
        styledText.endLine();
        styledText.addTextToLine(_48CharsString);
        styledText.endLine();
        styledText.addTextToLine(_48CharsString);
        styledText.endLine();
        styledText.addTextToLine(_48CharsString);
        styledText.endLine();
        styledText.endLine();


        styledText.setFontFace(Font_E.openGlFont_8x13);
        styledText.addTextToLine("OPENGL FONT 8x13, max 42 KARAKTER");
        styledText.endLine();
        styledText.addTextToLine("MONO SPACE");
        styledText.endLine();
        styledText.addTextToLine(_48CharsString);
        styledText.endLine();
        styledText.addTextToLine(_48CharsString);
        styledText.endLine();
        styledText.addTextToLine(_48CharsString);
        styledText.endLine();
        styledText.endLine();

        styledText.setFontFace(Font_E.SourceSansPro);
        styledText.setFontSize(8);
        styledText.addTextToLine("SOURCE SANS PRO 8 PUNTO");
        styledText.endLine();
        styledText.addTextToLine("HARFLERE BAĞLI DEĞİŞKEN KAPASİTE");
        styledText.endLine();
        styledText.addTextToLine("NOT MONO SPACE");
        styledText.endLine();
        styledText.addTextToLine(_48CharsString);
        styledText.endLine();
        styledText.addTextToLine(_48CharsString);
        styledText.endLine();
        styledText.addTextToLine(_48CharsString);
        styledText.endLine();
        styledText.endLine();

        styledText.setFontFace(Font_E.Go_Mono);
        styledText.setFontSize(8);
        styledText.addTextToLine("GO MONO 8 PUNTO, MAX 38 KARAKTER");
        styledText.endLine();
        styledText.addTextToLine("MONO SPACE");
        styledText.endLine();
        styledText.addTextToLine(_48CharsString);
        styledText.endLine();
        styledText.addTextToLine(_48CharsString);
        styledText.endLine();
        styledText.addTextToLine(_48CharsString);
        styledText.endLine();
        styledText.endLine();

        styledText.setFontFace(Font_E.Go_Mono_Bold);
        styledText.setFontSize(8);
        styledText.addTextToLine("GO MONO BOLD 8 PUNTO, MAX 38 KARAKTER");
        styledText.endLine();
        styledText.addTextToLine("MONO SPACE");
        styledText.endLine();
        styledText.addTextToLine(_48CharsString);
        styledText.endLine();
        styledText.addTextToLine(_48CharsString);
        styledText.endLine();
        styledText.addTextToLine(_48CharsString);
        styledText.endLine();
        styledText.endLine();

        styledText.setFontFace(Font_E.Go_Mono);
        styledText.setFontSize(7);
        styledText.addTextToLine("GO MONO 7 PUNTO, MAX 48 KARAKTER");
        styledText.endLine();
        styledText.addTextToLine("MONO SPACE");
        styledText.endLine();
        styledText.addTextToLine(_48CharsString);
        styledText.endLine();
        styledText.addTextToLine(_48CharsString);
        styledText.endLine();
        styledText.addTextToLine(_48CharsString);
        styledText.endLine();
        styledText.endLine();

        styledText.setFontFace(Font_E.Go_Mono_Bold);
        styledText.setFontSize(7);
        styledText.addTextToLine("GO MONO BOLD 7 PUNTO, MAX 48 KARAKTER");
        styledText.endLine();
        styledText.addTextToLine("MONO SPACE");
        styledText.endLine();
        styledText.addTextToLine(_48CharsString);
        styledText.endLine();
        styledText.addTextToLine(_48CharsString);
        styledText.endLine();
        styledText.addTextToLine(_48CharsString);
        styledText.endLine();
        styledText.endLine();

        styledText.addSpace(100);
        styledText.print(PrinterService.getService());
    }

    static PrinterDefinitions.PrinterErrorCode checkStatus()
    {
        try
        {
            return PrinterDefinitions.PrinterErrorCode.GetValue(PrinterService.getService().printerStatus());
        }
        catch (Exception e)
        {
            return PrinterDefinitions.PrinterErrorCode.DEAD_SERVICE;
        }
    }

    static void ToastStatus(Context context, PrinterDefinitions.PrinterErrorCode errCode)
    {
        String errorString;
        int textColor;
        if(errCode == PrinterDefinitions.PrinterErrorCode.NO_ERROR)
        {
            errorString = "Printer Status: Ready";
            textColor = 0xff5cb85c; // success green
        }
        else
        {
            errorString = "Printer Status: " + errCode.toString();
            textColor = 0xffd9534f; // error green
        }

        Toast toast = Toast.makeText(context, errorString, Toast.LENGTH_LONG);
        TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
        v.setTextColor(textColor);
        toast.show();
    }

    static void PrintExternalBitmapReceipt(final byte[] bitmapFile)
    {
        PrinterService.getService(printerService -> {

            if (printerService == null) {
                Log.d(TAG, "Printer Service was null");
                return;
            }
            printerService.printExternalBitmap(bitmapFile);
            printerService.addSpace(bottomMargin);
        });
    }

    static void PrintExternalBitmapReceiptWithStyledStringMethod(final byte[] bitmapFile)
    {
        StyledString styledText = new StyledString();
        styledText.printExternalBitmap(bitmapFile);
        styledText.addSpace(bottomMargin);
        styledText.print(PrinterService.getService());
    }

    static void PrintBitmapReceiptWithStyledStringMethod(final Context ctx, final byte[] bitmapFile)
    {
        String text = "Boktan işler";
        StyledString styledText = new StyledString();
        styledText.printBitmap(ctx, bitmapFile);
        styledText.setFontFace(Font_E.Sans_Semi_Bold);
        styledText.setFontSize(16);
        styledText.setLineSpacing(1.4f);
        styledText.addTextToLine("SOME RECEIPT", Alignment.Center);
        styledText.endLine();

        styledText.setFontFace(Font_E.SourceSansPro);
        styledText.setFontSize(14);
        styledText.setLineSpacing(1.1f);
        styledText.addTextToLine("Following qr code should read:");
        styledText.endLine();
        styledText.addTextToLine(text.length() > 25 ? text.substring(0,25) + "...": text);
        styledText.endLine();

        styledText.printQrCode(text, PrinterDefinitions.QR_Code_Error_Correction_Level.MEDIUM, 20);
        styledText.addSpace(bottomMargin);

        styledText.print(PrinterService.getService());
    }

    static void PrintBitmapReceiptWithAbsolutePathMethod(String absolutePath)
    {
        StyledString styledText = new StyledString();
        styledText.printBitmap(absolutePath);
        styledText.addSpace(bottomMargin);
        styledText.print(PrinterService.getService());
    }

    static void PrintQrWithDirectPrinterCommands(String text)
    {
        PrinterService.getService(printerService -> {

            if (printerService == null) {
                Log.d(TAG, "Printer Service was null");
                return;
            }

            printerService.setFontFace(Font_E.Sans_Semi_Bold.ordinal());
            printerService.setFontSize(16);
            printerService.setLineSpacing(1.4f);
            printerService.addTextToLine("SOME RECEIPT", Alignment.Center.ordinal());
            printerService.printLine();

            printerService.setFontFace(Font_E.SourceSansPro.ordinal());
            printerService.setFontSize(13);
            printerService.setLineSpacing(1.1f);
            printerService.printText("Following QR code should read:\n\"" + (text.length() > 25 ? text.substring(0,25) + "...": text) + "\"");
            printerService.printQrCode(text, PrinterDefinitions.QR_Code_Error_Correction_Level.MEDIUM.ordinal(), 20);
            printerService.addSpace(bottomMargin);
        });
    }

    static void PrintQrWithStyledString(String text)
    {
        StyledString styledText = new StyledString();

        styledText.setFontFace(Font_E.Sans_Semi_Bold);
        styledText.setFontSize(16);
        styledText.setLineSpacing(1.4f);
        styledText.addTextToLine("SOME RECEIPT", Alignment.Center);
        styledText.endLine();

        styledText.setFontFace(Font_E.SourceSansPro);
        styledText.setFontSize(14);
        styledText.setLineSpacing(1.1f);
        styledText.addTextToLine("Following qr code should read:");
        styledText.endLine();
        styledText.addTextToLine(text.length() > 25 ? text.substring(0,25) + "...": text);
        styledText.endLine();

        styledText.printQrCode(text, PrinterDefinitions.QR_Code_Error_Correction_Level.MEDIUM, 20);
        styledText.paperCut();
        //styledText.addSpace(bottomMargin);

        //Log.i(TAG, "PrintQrWithStyledString: \n" + styledText.toString());
        styledText.print(PrinterService.getService());
    }

}
