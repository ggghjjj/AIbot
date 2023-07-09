package com.kob.backend.service.impl.user.bot;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kob.backend.mapper.BotMapper;
import com.kob.backend.mapper.UserMapper;
import com.kob.backend.pojo.Bot;
import com.kob.backend.pojo.User;
import com.kob.backend.service.impl.utils.UserDetailsImpl;
import com.kob.backend.service.user.bot.AddService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class AddServiceImpl implements AddService{

    @Autowired
    private BotMapper botMapper;
    @Override
    public Map<String, String> add(Map<String, String> data) {
        System.out.println(data);
        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        UserDetailsImpl loginUser = (UserDetailsImpl) authenticationToken.getPrincipal();

        User user =loginUser.getUser();

        String title = data.get("title");
        String description = data.get("description");
        String content = data.get("content");

        Map<String,String> map = new HashMap<>();
        System.out.println(title);
        if(title==null||title.length()==0) {
            map.put("error_message","标题不能为空");
            return map;
        }

        if(title.length()>100) {
            map.put("error_message","标题长度不能大于100");
            return map;
        }

        if(description==null||description.length()==0) {
            description="这家伙很懒,什么也没留下";
        }

        if(description.length()>100) {
            map.put("error_message","bot长度不能大于300");
            return map;
        }
        if(content==null||content.length()==0) {
            map.put("error_message","代码不能为空");
            return map;
        }

        if (content.length() > 10000) {
            map.put("error_message", "代码长度不能超过10000");
            return map;
        }

        QueryWrapper<Bot> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",user.getId());
        if(botMapper.selectCount(queryWrapper)>=10) {
            map.put("error_message","每个用户最多只能有10个bot!");
            return map;
        }
        Date now = new Date();
        Bot bot = new Bot(null, user.getId(), title, description, content, now, now);
        botMapper.insert(bot);
        map.put("error_message","success");

        return map;
    }

    @Override
    public Map<String, String> getCode() {
        String code = "package com.kob.botrunningsystem.utils;\n" +
                "\n" +
                "import java.io.File;\n" +
                "import java.io.FileNotFoundException;\n" +
                "import java.util.ArrayList;\n" +
                "import java.util.List;\n" +
                "import java.util.Scanner;\n" +
                "\n" +
                "public class Bot implements java.util.function.Supplier<Integer> {\n" +
                "\n" +
                "    // 定义的地图方格\n" +
                "    static class Cell {\n" +
                "        public int x, y;\n" +
                "        public Cell(int x, int y) {\n" +
                "            this.x = x;\n" +
                "            this.y = y;\n" +
                "        }\n" +
                "    }\n" +
                "\n" +
                "   // 获取当前局面信息的\n" +
                "    @Override\n" +
                "    public Integer get() {\n" +
                "        File file = new File(\"input.txt\");\n" +
                "        try {\n" +
                "            Scanner sc = new Scanner(file);\n" +
                "            return nextMove(sc.next());\n" +
                "\n" +
                "        }catch (FileNotFoundException e){\n" +
                "            throw new RuntimeException(e);\n" +
                "        }\n" +
                "\n" +
                "    }\n" +
                "\n" +
                "    //判断长度是否会增加\n" +
                "    private boolean check_tail_increasing(int step) {  // 检验当前回合，蛇的长度是否增加\n" +
                "        if (step <= 10) return true;\n" +
                "        return step % 3 == 1;\n" +
                "    }\n" +
                "\n" +
                "    //获取 这个玩家的路径\n" +
                "    public List<Cell> getCells(int sx, int sy, String steps) {\n" +
                "        steps = steps.substring(1, steps.length() - 1);\n" +
                "        List<Cell> res = new ArrayList<>();\n" +
                "\n" +
                "        int[] dx = {-1, 0, 1, 0}, dy = {0, 1, 0, -1};\n" +
                "        int x = sx, y = sy;\n" +
                "        int step = 0;\n" +
                "        res.add(new Cell(x, y));\n" +
                "        for (int i = 0; i < steps.length(); i ++ ) {\n" +
                "            int d = steps.charAt(i) - '0';\n" +
                "            x += dx[d];\n" +
                "            y += dy[d];\n" +
                "            res.add(new Cell(x, y));\n" +
                "            if (!check_tail_increasing( ++ step)) {\n" +
                "                res.remove(0);\n" +
                "            }\n" +
                "        }\n" +
                "        return res;\n" +
                "    }\n" +
                "\n" +
                "    // 用户要实现的函数  来通过局面信息获取下一步蛇的操作 自动控制蛇的移动\n" +
                "    public Integer nextMove(String input) {\n" +
                "        String[] strs = input.split(\"#\");\n" +
                "        int[][] g = new int[13][14];\n" +
                "        for (int i = 0, k = 0; i < 13; i ++ ) {\n" +
                "            for (int j = 0; j < 14; j ++, k ++ ) {\n" +
                "                if (strs[0].charAt(k) == '1') {\n" +
                "                    g[i][j] = 1;\n" +
                "                }\n" +
                "            }\n" +
                "        }\n" +
                "\n" +
                "        // 获取到玩家的位置信息  a表示第一个玩家的 x,y坐标\n" +
                "        // b表示第二个玩家的位置坐标\n" +
                "        // 在传回数据的时候 自己永远是a玩家 因此我们只需要分析b玩家的位置情况和a玩家的位置情况 结合地图上的障碍物给出位置\n" +
                "        int aSx = Integer.parseInt(strs[1]), aSy = Integer.parseInt(strs[2]);\n" +
                "        int bSx = Integer.parseInt(strs[4]), bSy = Integer.parseInt(strs[5]);\n" +
                "\n" +
                "        List<Cell> aCells = getCells(aSx, aSy, strs[3]);\n" +
                "        List<Cell> bCells = getCells(bSx, bSy, strs[6]);\n" +
                "\n" +
                "        // 把当前玩家的身体也视为墙壁\n" +
                "        for (Cell c: aCells) g[c.x][c.y] = 1;\n" +
                "        for (Cell c: bCells) g[c.x][c.y] = 1;\n" +
                "\n" +
                "\n" +
                "        //到达这一步时  g表示的是当前地图上的信息 0表示能走的位置 1表示不能走的位置\n" +
                "        // acell表示自己走过的路\n" +
                "        // bcell表示对方走过的路\n" +
                "        // 这时候用户可以通过这几个信息 通过获取自己最后一步的位置 并根据局面信息 来返回相应的结果\n" +
                "        // 0 1 2 3 分别对应 上 右 下 左\n" +
                "\n" +
                "\n" +
                "        // dx dy 分别代表 下一步要走的横纵坐标\n" +
                "        int[] dx = {-1, 0, 1, 0}, dy = {0, 1, 0, -1};\n" +
                "\n" +
                "\n" +
                "        // 玩家在这里编写代码区\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "        //玩家编写代码区结束\n" +
                "\n" +
                "\n" +
                "        // 默认每次返回上方向\n" +
                "        return 0;\n" +
                "    }\n" +
                "}\n";
        Map<String,String> map = new HashMap<>();
        map.put("error_message","success");
        map.put("code",code);
        return map;
    }
}
