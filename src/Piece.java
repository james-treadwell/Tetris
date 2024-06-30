public class Piece {

    public static int[][] Pieces(int x){
        int[][] ret = new int[4][4];
        //I-BLOCK
        if(x == 0){
            ret[0][1] = 1;
            ret[1][1] = 1;
            ret[2][1] = 1;
            ret[3][1] = 1;
        }
        //J-BLOCK
        else if (x == 1){
            ret[0][0] = 1;
            ret[1][0] = 1;
            ret[1][1] = 1;
            ret[1][2] = 1;
        }
        //L-BLOCK
        else if (x == 2){
            ret[0][0] = 1;
            ret[2][0] = 1;
            ret[1][0] = 1;
            ret[2][1] = 1;
        }
        //O-BLOCK
        else if (x == 3){
            ret[0][0] = 1;
            ret[0][1] = 1;
            ret[1][0] = 1;
            ret[1][1] = 1;
        }
        //S-BLOCK
        else if (x == 4){
            ret[0][1] = 1;
            ret[0][2] = 1;
            ret[1][0] = 1;
            ret[1][1] = 1;

        }
        //T-BLOCK
        else if (x == 5){
            ret[0][1] = 1;
            ret[1][0] = 1;
            ret[1][1] = 1;
            ret[1][2] = 1;
        }
        //Z-BLOCK
        else if(x == 6){
            ret[0][0] = 1;
            ret[0][1] = 1;
            ret[1][1] = 1;
            ret[1][2] = 1;
        }
        //I-BLOCK 180'
        else if (x == 7){
            ret[1][0] = 1;
            ret[1][1] = 1;
            ret[1][2] = 1;
            ret[1][3] = 1;
        }
        //J-BLOCK 90'
        else if (x == 8){
            ret[0][1] = 1;
            ret[0][2] = 1;
            ret[1][1] = 1;
            ret[2][1] = 1;
        }
        //J-BLOCK 180'
        else if (x == 9){
            ret[0][0] = 1;
            ret[0][1] = 1;
            ret[0][2] = 1;
            ret[1][2] = 1;
        }
        //J-BLOCK 270'
        else if (x == 10){
            ret[0][1] = 1;
            ret[1][1] = 1;
            ret[2][0] = 1;
            ret[2][1] = 1;
        }
        //L-BLOCK 90'
        else if (x == 11){
            ret[0][0] = 1;
            ret[0][1] = 1;
            ret[0][2] = 1;
            ret[1][0] = 1;
        }
        //L-BLOCK 180'
        else if (x == 12){
            ret[0][0] = 1;
            ret[0][1] = 1;
            ret[1][1] = 1;
            ret[2][1] = 1;
        }
        //L-BLOCK 270'
        else if (x == 13){
            ret[0][2] = 1;
            ret[1][0] = 1;
            ret[1][1] = 1;
            ret[1][2] = 1;
        }
        //S-BLOCK 180'
        else if (x == 14){
            ret[0][0] = 1;
            ret[1][0] = 1;
            ret[1][1] = 1;
            ret[2][1] = 1;
        }
        //T-BLOCK 90'
        else if (x == 15){
            ret[0][0] = 1;
            ret[1][0] = 1;
            ret[1][1] = 1;
            ret[2][0] = 1;
        }
        //T-BLOCK 180'
        else if (x == 16){
            ret[0][0] = 1;
            ret[0][1] = 1;
            ret[0][2] = 1;
            ret[1][1] = 1;
        }
        //T-BLOCK 270'
        else if (x == 17){
            ret[0][1] = 1;
            ret[1][0] = 1;
            ret[1][1] = 1;
            ret[2][1] = 1;
        }
        //Z-BLOCK 180'
        else if (x == 18){
            ret[0][1] = 1;
            ret[1][0] = 1;
            ret[1][1] = 1;
            ret[2][0] = 1;
        }
        return ret;
    }
}
