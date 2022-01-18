
import com.sun.org.apache.xalan.internal.xsltc.compiler.Pattern;

import java.util.*;
import java.util.stream.Collectors;

public class Main {
    static Tile[][] sboard;
    static int sm;
    static int sn;
    static List<Tile> tiles = new ArrayList<>();


    public static String solution(int m, int n, String[] board) {
        String answer = "";

        sboard = new Tile[m][n];
        sm = m;
        sn = n;

        //set symbol
        for(int i = 0; i < m; i++) {
            for(int j = 0; j < n; j++) {
                sboard[i][j] = getTileInstance(board[i].charAt(j));
            }
        }

        //set paths
        for(int i = 0; i < m; i++) {
            for(int j = 0; j < n; j++) {
                if(!sboard[i][j].isMatched && sboard[i][j].isAlphabet) {
                    int[] pos = positionToBeMatched(sboard[i][j]);
                    int i2 = pos[0];
                    int j2 = pos[1];

                    sboard[i][j].setPaths(getPaths(i, j, i2, j2));
                    sboard[i][j].isMatched = true;
                    tiles.add(sboard[i][j]);
                }
            }
        }


        StringBuilder sb = new StringBuilder();

        while(true) {
            if(tiles.isEmpty()) break;

            Collections.sort(tiles);
            if(!tiles.get(0).isRemovable) return "IMPOSSIBLE";
            sb.append(tiles.get(0).symbol);

            for(Tile tile : tiles ){
                tile.removePathSymbol(tiles.get(0).symbol);
            }
            tiles.remove(tiles.get(0));
        }
        return sb.toString();
    }

    //sharing ref
    public static Tile getTileInstance(Character c) {
        for(int i = 0; i < sm; i++) {
            for(int j = 0; j < sn; j++) {
                if(sboard[i][j] instanceof Tile && sboard[i][j].symbol == c) {
                    return sboard[i][j];
                }
            }
        }
        return new Tile(c);
    }

    //뒤에서부터 탐색, returns {m, n}
    public static int[] positionToBeMatched(Tile tile) {
        for(int i = sm - 1; i >= 0; i--) {
            for(int j = sn - 1; j >= 0; j--) {
                if(sboard[i][j].equals(tile)) {
                    return new int[]{i,j};
                }
            }
        }
        return null;
    }

    // sboard[am][an] -> sboard[bm][bn] 경로상 모든 Tile 종류. 시작,끝 미포함
    public static Set<Set<Character>> getPaths (int am, int an, int bm, int bn) {
        Set<Set<Character>> paths = new HashSet<>();

        if(am == bm) {
            Set<Character> path = getHPath(am, an, bn);
            path.remove(sboard[am][an].symbol);
            paths.add(path);

        }else if(an == bn) {
            Set<Character> path = getVPath(am, bm, an);
            path.remove(sboard[am][an].symbol);
            paths.add(path);

        }else {
            Set<Character> path1, path2;
            path1 = getHPath(am, an, bn); // A -> B horizontally
            path1.addAll(getVPath(bm, am, bn)); // B -> A vertically
            path1.remove(sboard[am][an].symbol);

            path2 = getVPath(am, bm, an); // A -> B vertically
            path2.addAll(getHPath(bm, bn, an)); // B -> A horizontally
            path2.remove(sboard[am][an].symbol);

            paths.add(path1);
            paths.add(path2);
        }

        return paths;
    }

    //horizontal
    public static Set<Character> getHPath (int m, int an, int bn) {
        Set<Character> path = new HashSet<>();
        for(int i = Math.min(an, bn); i <= Math.max(an, bn); i++) {
            path.add(sboard[m][i].symbol);
        }
        return path;
    }
    //vertical
    public static Set<Character> getVPath (int am, int bm, int n) {
        Set<Character> path = new HashSet<>();
        for(int i = Math.min(am, bm); i <= Math.max(am, bm); i++) {
            path.add(sboard[i][n].symbol);
        }
        return path;
    }


    static class Tile implements Comparable<Tile>{
        Character symbol;
        Set<Set<Character>> paths = new HashSet<>();
        boolean isMatched = false;
        boolean isRemoved = false;
        boolean isAlphabet = true;
        Boolean isRemovable = false;



        Tile (Character symbol) {
            this.symbol = symbol;
            if(symbol == '*' || symbol == '.') {
                isAlphabet = false;
            }
        }

        boolean setPaths (Set<Set<Character>> paths) {
            for(Set<Character> path : paths) {
                this.paths.add(path);

            }
            updateRemovable();
            return isRemovable;
        }

        boolean isPathContains(Character symbol) {
            for(Set<Character> path : paths) {
                for(Character c : path) {
                    if(symbol == c) return true;
                }
            }
            return false;
        }

        void updateRemovable () {

            for(Set<Character> path : paths) {
                if(path.size() == 0 || (path.size() == 1 && path.contains('.'))) {
                    isRemovable = true;
                    return;
                }
            }
            isRemovable = false;
        }

        boolean removePathSymbol (Character c){ // returns isRemovable;
            for(Set<Character> path : paths) {
                path.remove(c);
            }
            updateRemovable();
            return isRemovable;
        }
        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Tile ) {
                Tile tile = (Tile) obj;
                if(symbol.equals(tile.symbol)) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public int compareTo(Tile o) {
            if(!isRemovable.equals(o.isRemovable)) {
                return o.isRemovable.compareTo(isRemovable);// true 먼저 오도록
            }else {
                return symbol - o.symbol;
            }
        }
    }


    public static void main(String args[]) {
        System.out.println(solution(2,4, new String[] {"NRYN", "ARYA"}));
    }

}
