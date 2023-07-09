package com.kob.botrunningsystem.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Bot implements java.util.function.Supplier<Integer> {

    // 定义的地图方格
    static class Cell {
        public int x, y;
        public Cell(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

   // 获取当前局面信息的
    @Override
    public Integer get() {
        File file = new File("input.txt");
        try {
            Scanner sc = new Scanner(file);
            return nextMove(sc.next());

        }catch (FileNotFoundException e){
            throw new RuntimeException(e);
        }

    }

    //判断长度是否会增加
    private boolean check_tail_increasing(int step) {  // 检验当前回合，蛇的长度是否增加
        if (step <= 10) return true;
        return step % 3 == 1;
    }

    //获取 这个玩家的路径
    public List<Cell> getCells(int sx, int sy, String steps) {
        steps = steps.substring(1, steps.length() - 1);
        List<Cell> res = new ArrayList<>();

        int[] dx = {-1, 0, 1, 0}, dy = {0, 1, 0, -1};
        int x = sx, y = sy;
        int step = 0;
        res.add(new Cell(x, y));
        for (int i = 0; i < steps.length(); i ++ ) {
            int d = steps.charAt(i) - '0';
            x += dx[d];
            y += dy[d];
            res.add(new Cell(x, y));
            if (!check_tail_increasing( ++ step)) {
                res.remove(0);
            }
        }
        return res;
    }

    // 用户要实现的函数  来通过局面信息获取下一步蛇的操作 自动控制蛇的移动
    public Integer nextMove(String input) {
        String[] strs = input.split("#");
        int[][] g = new int[13][14];
        for (int i = 0, k = 0; i < 13; i ++ ) {
            for (int j = 0; j < 14; j ++, k ++ ) {
                if (strs[0].charAt(k) == '1') {
                    g[i][j] = 1;
                }
            }
        }

        // 获取到玩家的位置信息  a表示第一个玩家的 x,y坐标
        // b表示第二个玩家的位置坐标
        // 在传回数据的时候 自己永远是a玩家 因此我们只需要分析b玩家的位置情况和a玩家的位置情况 结合地图上的障碍物给出位置
        int aSx = Integer.parseInt(strs[1]), aSy = Integer.parseInt(strs[2]);
        int bSx = Integer.parseInt(strs[4]), bSy = Integer.parseInt(strs[5]);

        List<Cell> aCells = getCells(aSx, aSy, strs[3]);
        List<Cell> bCells = getCells(bSx, bSy, strs[6]);

        // 把当前玩家的身体也视为墙壁
        for (Cell c: aCells) g[c.x][c.y] = 1;
        for (Cell c: bCells) g[c.x][c.y] = 1;


        //到达这一步时  g表示的是当前地图上的信息 0表示能走的位置 1表示不能走的位置
        // acell表示自己走过的路
        // bcell表示对方走过的路
        // 这时候用户可以通过这几个信息 通过获取自己最后一步的位置 并根据局面信息 来返回相应的结果
        // 0 1 2 3 分别对应 上 右 下 左


        // dx dy 分别代表 下一步要走的横纵坐标
        int[] dx = {-1, 0, 1, 0}, dy = {0, 1, 0, -1};



        for (int i = 0; i < 4; i ++ ) {
            int x = aCells.get(aCells.size() - 1).x + dx[i];
            int y = aCells.get(aCells.size() - 1).y + dy[i];
            // 不超过地图且下一步是路的情况下 才能走
            if (x >= 0 && x < 13 && y >= 0 && y < 14 && g[x][y] == 0) {
                return i;
            }
        }
        // 默认每次返回上方向
        return 0;
    }
}
