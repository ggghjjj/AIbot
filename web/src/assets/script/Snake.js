import { AcGameObject } from "./AcGameObject";
import { Cell } from "./Cell";

export class Snake extends AcGameObject {
    constructor(info,gamemap) {
        super();
        this.color = info.color;

        this.r = info.r;
        this.c = info.c;
        this.gamemap = gamemap;
        this.Cells = [
            new Cell(this.r,this.c),
        ]
        this.speed =5;

        this.next_cell = null;
        this.direction = -1;
        this.status = "idle";
        // this.dx = [-1,0,1,0];

        // this.dy = [0,1,0,-1];
        this.dr = [-1, 0, 1, 0];  // 4个方向行的偏移量
        this.dc = [0, 1, 0, -1];  // 4个方向列的偏移量


        this.step=0;
        this.eps=  1e-2;

        this.eye_direction = 0;
        if (this.id === 1) this.eye_direction = 2;  // 左下角的蛇初始朝上，右上角的蛇朝下

        this.eye_dx = [  // 蛇眼睛不同方向的x的偏移量
            [-1, 1],
            [1, 1],
            [1, -1],
            [-1, -1],
        ];
        this.eye_dy = [  // 蛇眼睛不同方向的y的偏移量
            [-1, -1],
            [-1, 1],
            [1, 1],
            [1, -1],
        ]

    }

    start() {

    }

    set_direction(d) {
        console.log(d);
        this.direction = d;
    }

    next_step() {  // 将蛇的状态变为走下一步
        const d = this.direction;
        this.next_cell = new Cell(this.Cells[0].r + this.dr[d], this.Cells[0].c + this.dc[d]);
        this.eye_direction = d;
        this.direction = -1;  // 清空操作
        this.status = "move";
        this.step ++ ;

        const k = this.Cells.length;
        for (let i = k; i > 0; i -- ) {
            this.Cells[i] = JSON.parse(JSON.stringify(this.Cells[i - 1]));
        }

   
        if (!this.gamemap.check_valid(this.next_cell)) {  // 下一步操作撞了，蛇瞬间去世
            this.status = "die";
        }
    }

    update_move() {
        const dx = this.next_cell.x - this.Cells[0].x;
        const dy = this.next_cell.y - this.Cells[0].y;
        const distance = Math.sqrt(dx * dx + dy * dy);

        if (distance < this.eps) {  // 走到目标点了
            this.Cells[0] = this.next_cell;  // 添加一个新蛇头
            this.next_cell = null;
            this.status = "idle";  // 走完了，停下来

            if (!this.check_tail_increasing()) {  // 蛇不变长
                this.Cells.pop();
            }
        } else {
            const move_distance = this.speed * this.timedelta / 1000;  // 每两帧之间走的距离
            this.Cells[0].x += move_distance * dx / distance;
            this.Cells[0].y += move_distance * dy / distance;

            if (!this.check_tail_increasing()) {
                const k = this.Cells.length;
                const tail = this.Cells[k - 1], tail_target = this.Cells[k - 2];
                const tail_dx = tail_target.x - tail.x;
                const tail_dy = tail_target.y - tail.y;
                tail.x += move_distance * tail_dx / distance;
                tail.y += move_distance * tail_dy / distance;
            }
        }
    }


    check_tail_increasing() {  // 检测当前回合，蛇的长度是否增加
        if (this.step <= 10) return true;
        if (this.step % 3 === 1) return true;
        return false;
    }

    update() {
        if (this.status === 'move') {
            this.update_move();
        }

        this.render();
    }

    render() {
        const L = this.gamemap.L;
        const ctx = this.gamemap.ctx;

        ctx.fillStyle = this.color;
        if (this.status === "die") {
            ctx.fillStyle = "white";
        }

        for (const cell of this.Cells) {
            ctx.beginPath();
            ctx.arc(cell.x * L, cell.y * L, L / 2 * 0.8, 0, Math.PI * 2);
            ctx.fill();
        }

        for (let i = 1; i < this.Cells.length; i ++ ) {
            const a = this.Cells[i - 1], b = this.Cells[i];
            if (Math.abs(a.x - b.x) < this.eps && Math.abs(a.y - b.y) < this.eps)
                continue;
            if (Math.abs(a.x - b.x) < this.eps) {
                ctx.fillRect((a.x - 0.4) * L, Math.min(a.y, b.y) * L, L * 0.8, Math.abs(a.y - b.y) * L);
            } else {
                ctx.fillRect(Math.min(a.x, b.x) * L, (a.y - 0.4) * L, Math.abs(a.x - b.x) * L, L * 0.8);
            }
        }


        ctx.fillStyle = "black";
        for (let i = 0; i < 2; i ++ ) {
            const eye_x = (this.Cells[0].x + this.eye_dx[this.eye_direction][i] * 0.15) * L;
            const eye_y = (this.Cells[0].y + this.eye_dy[this.eye_direction][i] * 0.15) * L;

            ctx.beginPath();
            ctx.arc(eye_x, eye_y, L * 0.05, 0, Math.PI * 2);
            ctx.fill();
        }
    }
}