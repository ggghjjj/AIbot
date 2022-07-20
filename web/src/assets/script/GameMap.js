import { AcGameObject } from "./AcGameObject";
import { Snake } from "./Snake";
import { Wall } from "./Wall";

export class GameMap extends AcGameObject {
    constructor(ctx,parent) {
        super();
        
        this.ctx = ctx;
        this.parent = parent;
        this.L = 0;
        this.cols = 13;
        this.rows = 14;
        this.walls = [];
        this.inner_walls_count = 20;

        this.Snakes= [
            new Snake({id:0,color:"#4876EC",r:this.rows-2,c:1},this),
            new Snake({id:1,color:"#F94848",r:1,c:this.cols-2},this)
        ]
    }

    
    check_connectivity(g, sx, sy, tx, ty) {
        if (sx == tx && sy == ty) return true;
        g[sx][sy] = true;

        let dx = [-1, 0, 1, 0], dy = [0, 1, 0, -1];
        for (let i = 0; i < 4; i ++ ) {
            let x = sx + dx[i], y = sy + dy[i];
            if (!g[x][y] && this.check_connectivity(g, x, y, tx, ty))
                return true;
        }

        return false;
    }


    create_walls() {
        const g = [];
        for (let r = 0; r < this.rows; r ++ ) {
            g[r] = [];
            for (let c = 0; c < this.cols; c ++ ) {
                g[r][c] = false;
            }
        }

        // 给四周加上障碍物
        for (let r = 0; r < this.rows; r ++ ) {
            g[r][0] = g[r][this.cols - 1] = true;
        }

        for (let c = 0; c < this.cols; c ++ ) {
            g[0][c] = g[this.rows - 1][c] = true;
        }

        for(let cnt = 0;cnt < this.inner_walls_count;cnt++) {
            for(let i = 0;i<1000;i++) {
                let r = parseInt(Math.random() * this.rows);
                let c = parseInt(Math.random() * this.cols);
                if(g[r][c] || g[this.rows-1-r][this.cols-1-c]) continue;

                if(c==1&&r==this.rows-2 || c==this.cols-2 && r ==1) continue;
                g[r][c] = g[this.rows-1-r][this.cols-1-c] = true;
                break;
            }
        }

        const copy_g = JSON.parse(JSON.stringify(g));
        if (!this.check_connectivity(copy_g, this.rows - 2, 1, 1, this.cols - 2))
            return false;


        for (let r = 0; r < this.rows; r ++ ) {
            for (let c = 0; c < this.cols; c ++ ) {
                if (g[r][c]) {
                    this.walls.push(new Wall(r, c, this));
                }
            }
        }

        return true;
    }

    check_valid(cell) {  // 检测目标位置是否合法：没有撞到两条蛇的身体和障碍物
        for (const wall of this.walls) {
            if (wall.r === cell.r && wall.c === cell.c)
                return false;
        }

        for (const snake of this.Snakes) {
            let k = snake.Cells.length;
            if (!snake.check_tail_increasing()) {  // 当蛇尾会前进的时候，蛇尾不要判断
                k -- ;
            }
            for (let i = 0; i < k; i ++ ) {
                if (snake.Cells[i].r === cell.r && snake.Cells[i].c === cell.c)
                    return false;
            }
        }

        return true;
    }


    add_listening_events() {
        
        this.ctx.canvas.focus();
        const [snake0, snake1] = this.Snakes;
        this.ctx.canvas.addEventListener("keydown", e => {
            console.log(e.key);
            if (e.key === 'w') snake0.set_direction(0);
            else if (e.key === 'd') snake0.set_direction(1);
            else if (e.key === 's') snake0.set_direction(2);
            else if (e.key === 'a') snake0.set_direction(3);
            else if (e.key === 'ArrowUp') snake1.set_direction(0);
            else if (e.key === 'ArrowRight') snake1.set_direction(1);
            else if (e.key === 'ArrowDown') snake1.set_direction(2);
            else if (e.key === 'ArrowLeft') snake1.set_direction(3);
        });
    }

    start() {
        for (let i = 0; i < 1000; i ++ ) 
            if (this.create_walls())
                break;
        
        this.add_listening_events();
    }


    check_ready() {
        for (const snake of this.Snakes) {
            if (snake.status !== "idle") return false;
            
            if (snake.direction === -1) return false;
        }
        return true;

    }
    update_size() {
        this.L = parseInt(Math.min(this.parent.clientWidth / this.cols,this.parent.clientHeight / this.rows));
        this.ctx.canvas.width = this.L * this.cols;
        this.ctx.canvas.height = this.L * this.rows;
    }


    next_step() {
        for(const snake of this.Snakes) {
            console.log(snake.direction);
           snake.next_step();
        }
    }

    update() {
        this.update_size();
       
        if(this.check_ready()) {
            this.next_step();
        }
        this.render();
    }

    render() {
        const color_even = "#AAD751", color_odd = "#A2D149";
        for(let i = 0;i<this.rows;i++) {
            for(let j = 0;j<this.cols;j++) {
                if((i+j)%2==0) {
                    this.ctx.fillStyle= color_even ;
                }else {
                    this.ctx.fillStyle= color_odd ;
                }
                this.ctx.fillRect(this.L * i,this.L*j,this.L,this.L);

            }
        }
        
        
    }
}