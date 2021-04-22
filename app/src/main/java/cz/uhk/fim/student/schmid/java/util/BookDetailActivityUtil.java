package cz.uhk.fim.student.schmid.java.util;

import android.content.Context;

import cz.uhk.fim.student.schmid.R;

public class BookDetailActivityUtil {
    public static String getPiecesString(Context c, int num){
        CharSequence str;
        switch (num){
            case 1:
                str = c.getText(R.string.one_piece);
            break;
            case 2: case 3: case 4:
                str = c.getText(R.string.two_to_four_pieces);
            break;
            default:
                str = c.getText(R.string.five_and_more_pieces);
        }
        return str.toString();
    }

    public static String getPiecesLeftString(Context c, int num){
        CharSequence str;
        switch (num){
            case 1:
                str = c.getText(R.string.one_piece_left);
                break;
            case 2: case 3: case 4:
                str = c.getText(R.string.two_to_four_pieces_left);
                break;
            default:
                str = c.getText(R.string.five_and_more_pieces_left);
        }
        return str.toString();
    }
}
