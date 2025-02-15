import asyncio
import getpass
import json
import os
import websockets
import random

count = 1
directions_priorities = {"w":[8,0], "d":[8,0], "s":[8,0], "a":[8,0]}
directions_cost = {"w":0, "d":0, "s":0, "a":0}
zigzag_row = "d"
zigzag_column = "s"
range_dif_count = [0, ""]
moves = []
chasing_flag = False

# retorna a proxima posiçao de acordo com a direçao
def move_in_direction(pos, direction):
    if direction == "w":
        return (pos[0], pos[1] - 1)
    elif direction == "d":
        return (pos[0] + 1, pos[1])
    elif direction == "s":
        return (pos[0], pos[1] + 1)
    elif direction == "a":
        return (pos[0] - 1, pos[1])

# calcula a distancia entre duas posicoes
# distancia de Manhattan
def heuristic(a, b):
    return abs(a[0] - b[0]) + abs(a[1] - b[1])

def a_star_search(snake, target, grid_size=(48, 24)):
    global obstacles
    open_list = [(0, snake[0], [])]  # (cost, current position, path)
    cost_so_far = {snake[0]: 0}
    visited = set()

    while open_list:
        open_list.sort(key=lambda x: x[0])
        _, current, path = open_list.pop(0)

        # Early exit if the target is reached
        if current == target:
            return path + [current]

        # Avoid revisiting nodes
        if current in visited:
            continue
        visited.add(current)

        for direction in ["w", "d", "s", "a"]:
            # ve as posiçoes possiveis para os possiveis movimentos
            next_pos = move_in_direction(current, direction)
            # ve se dentro do mapa
            if not in_bounds(next_pos, grid_size) or next_pos in obstacles:
                continue

            new_cost = cost_so_far[current] + 1
            # ver se next_pos nao foi visitado ainda
            # e se o custo até ele é menor que o custo atual
            if next_pos not in cost_so_far or new_cost < cost_so_far[next_pos]:
                cost_so_far[next_pos] = new_cost
                priority = new_cost + heuristic(target, next_pos)
                open_list.append((priority, next_pos, path + [current]))

    return []  # Return an empty path if no solution is found


def in_bounds(pos, grid_size):
    return 0 <= pos[0] < grid_size[0] and 0 <= pos[1] < grid_size[1]

def def_limits(snake, current_aim, range):
    if current_aim == "a":
        order_directions_priorities(["d"], 1)
    elif current_aim == "d":
        order_directions_priorities(["a"], 1)
    elif current_aim == "s":
        order_directions_priorities(["w"], 1)
    else:
        order_directions_priorities(["s"], 1)
    def_map_limits(snake, range)

def def_map_limits(snake, range):
    global zigzag_row, zigzag_column, range_dif_count, chasing_flag, transverse
    range = range - 1

    if snake[0][0] - range <= 0:
        if snake[0][0] == 0:
            if not transverse:
                order_directions_priorities(["a"], 1)
            else:
                order_directions_priorities(["a"], 5)
        else:
            if not chasing_flag:
                order_directions_priorities(["a"], 5)
        zigzag_row = "d"
        range_dif_count[1] = zigzag_column
        range_dif_count[0] = range_dif_count[0] - 1
    elif snake[0][0] + range >= 47:
        if snake[0][0] == 47:
            if not transverse:
                order_directions_priorities(["d"], 1)
            else:
                order_directions_priorities(["d"], 5)
        else:
            if not chasing_flag:
                order_directions_priorities(["d"], 5)
        zigzag_row = "a"
        range_dif_count[1] = zigzag_column
        range_dif_count[0] = range_dif_count[0] - 1
    if snake[0][1] - range <= 0:
        if snake[0][1] == 0:
            if not transverse:
                order_directions_priorities(["w"], 1)
            else:
                order_directions_priorities(["w"], 5)
        else:
            if not chasing_flag:
                order_directions_priorities(["w"], 5)
        zigzag_column = "s"
    elif snake[0][1] + range >= 23:
        if snake[0][1] == 23:
            if not transverse:
                order_directions_priorities(["s"], 1)
            else:
                order_directions_priorities(["s"], 5)
        else:
            if not chasing_flag:
                order_directions_priorities(["s"], 5)
        zigzag_column = "w"

def change_row(snake):
    
    global zigzag_row, zigzag_column, range_dif_count, search_moves
    order_directions_priorities(zigzag_row, 13)
    if snake[0][0] == 0:
        order_directions_priorities(zigzag_column, 13)
    elif snake[0][0] == 47:
        order_directions_priorities(zigzag_column, 13)
    else:
        number = 1 if zigzag_row == 'd' else -1
        #if (snake[0][0] + number, snake[1]):
        #    search_moves = a_star_search(snake, [snake[0][1], snake[0][0]+number*2])


def sight_control(snake, sight, steps):
    global obstacles, transverse, chasing_flag
    food_coordinates = []
    for x in sight:
        for y in sight[x]:
            pos = (int(x), int(y))
            # wall
            if sight[x][y] == 1:
                # traverse = False -> desviar
                if not transverse and abs(int(x)-snake[0][0])+abs(int(y)-snake[0][1])<=1:
                    order_directions_priorities(get_aim(pos, snake)[0], 1)
                obstacles.add(pos)
            # fruits
            elif sight[x][y] == 2:
                food_coordinates.append(pos)
            # super fruits
            elif sight[x][y] == 3:
                # steps < 2700 and traverse = True-> desviar
                if abs(int(x)-snake[0][0])+abs(int(y)-snake[0][1])<=1 and steps < 2000 and transverse:
                    order_directions_priorities(get_aim(pos, snake)[0], 2)
                else:
                    # traverse = False or steps > 2700 -> eat super fruits
               	    if steps > 2000 or not transverse:
                        food_coordinates.append(pos)
            # other snake in the same row/column
            if sight[x][y] == 4:
                if list(pos) in snake:
                    if (((snake[0][0] == int(x) or snake[0][1] == int(y)) and (snake[0] != list(pos)))):
                            # melhor custo para desviar
                            if heuristic(pos,snake[0])<directions_cost[get_aim(pos,snake)[0]]:
                                directions_cost[get_aim(pos,snake)] = heuristic(pos,snake[0])
                else:
                        if (heuristic(snake[0], pos) == 1):
                            order_directions_priorities(get_aim(pos,snake)[0], 1)
                        elif (heuristic(snake[0], pos) == 2):
                            random_list = get_aim(pos, snake)
                            for i in random_list:
                                order_directions_priorities(i, 3)
    # se tiver comida , ir para ela
    if food_coordinates:
        chasing_flag = True
        order_directions_priorities(get_aim(food_coordinates[0], snake)[0], 13)
    else:
        chasing_flag = False

def def_body_limits(snake):
    for i in snake[1:]:
        if abs(i[0] - snake[0][0]) + abs(i[1] - snake[0][1]) <= 1:
            order_directions_priorities(get_aim(i, snake)[0], 1)

def get_aim(pos, snake):
    x_difference = pos[0]-snake[0][0]
    y_difference = pos[1]-snake[0][1]
    aim = []
    axis = 0 if abs(x_difference) < abs(y_difference) else 2 if abs(x_difference) == abs(y_difference) else 1
    if axis == 0:
        aim.append("s" if y_difference > 0 else "w")
    elif axis == 1:
        aim.append("d" if x_difference > 0 else "a")
    else:
        aim.extend(["d" if x_difference > 0 else "a", "s" if y_difference > 0 else "w"])
    return aim

def order_directions_priorities(keys, strength):
    global count
    global directions_priorities
    for key in keys:
        if strength<8 and directions_priorities[key][0]>strength and key != None:
            directions_priorities[key][0]=strength
            directions_priorities[key][1]=count
        elif directions_priorities[key][0]>=8 and (directions_priorities[key][0]<strength or count == 0) and key != None:
            directions_priorities[key][0]=strength
            directions_priorities[key][1]=count
        count+=1

# retorna lista com sorted direçoes por custo
def order_safe_directions(safe_directions, directions_costs):
    def get_cost(move):
        return directions_costs.get(move, float('inf'))
    sorted_safe_directions = sorted(safe_directions, key=get_cost)
    return sorted_safe_directions

# escolhe direçao conforme as prioridades, else random move
def choose_direction(body, sight):
    global directions_priorities, directions_cost
    # Sort directions by three criteria:
    # 1. Priority (higher is better -> descending order of directions_priorities[k][0])
    # 2. Cost (lower is better -> ascending order of directions_cost[k])
    # 3. Secondary priority value (lower is better -> ascending order of directions_priorities[k][1])
    sorted_directions = sorted(
        directions_priorities.keys(),
        key=lambda k: (
            -directions_priorities[k][0],  # Primary: higher priority first
            directions_cost.get(k, float('inf')),  # Secondary: lower cost first
            directions_priorities[k][1]  # Tertiary: lower secondary priority value
        )
    )

    return best_direction(sorted_directions, body, sight)

def best_direction(sorted_directions, snake_body, sight):

    global transverse, obstacles

    # Caminho também tem de evitar outras cobras
    snake_lst = []

    for x in sight:
        for y in sight[x]:
            if sight[x][y] == 4:
                snake_lst.append((int(x), int(y)))

    x, y = snake_body[0]
    possible_moves = {
        "d": ((x + 1) , y),  # Direita
        "a": ((x - 1) , y),  # Esquerda
        "s": (x, (y + 1) ),  # Baixo
        "w": (x, (y - 1) )   # Cima
    }

    safe_moves = [
        direction for direction, next_pos in possible_moves.items()
        if (next_pos not in map(tuple, snake_body) and next_pos not in snake_lst) and (transverse or next_pos not in obstacles)
    ]

    future_safe_moves = []
    for d in safe_moves:
        x_new, y_new = possible_moves[d]

        future_possible_moves = {
            "d": ((x_new + 1) , y_new),  # Direita
            "a": ((x_new - 1) , y_new),  # Esquerda
            "s": (x_new, (y_new + 1) ),  # Baixo
            "w": (x_new, (y_new - 1) )   # Cima
        }

        for direction, next_pos in future_possible_moves.items():
            if (next_pos not in map(tuple, snake_body[:-1]) and next_pos not in snake_lst) and (transverse or next_pos not in obstacles):
                future_safe_moves.append(d)
    
    # notar que a cobra inimiga tb se move ou seja pode ja nao estar naquela posiçao

    maybe_safe_moves = [
        direction for direction, next_pos in possible_moves.items()
        if next_pos in snake_lst
    ]

    future_maybe_safe_moves = []
    for d in maybe_safe_moves:
        x_new, y_new = possible_moves[d]

        future_possible_moves = {
            "d": ((x_new + 1) , y_new),  # Direita
            "a": ((x_new - 1) , y_new),  # Esquerda
            "s": (x_new, (y_new + 1) ),  # Baixo
            "w": (x_new, (y_new - 1) )   # Cima
        }

        for direction, next_pos in future_possible_moves.items():
            if next_pos in snake_lst:
                future_maybe_safe_moves.append(direction)
    """
    for direction in sorted_directions:
        if direction in future_safe_moves:
            return direction
        elif direction in safe_moves:
            return direction
        elif direction in future_maybe_safe_moves:
            return direction
        elif direction in maybe_safe_moves:
            return direction
        else:
            return random.choice(["w", "d", "s", "a"])
    """

    # choose the safest direction
    for d in sorted_directions:
        if d in future_safe_moves:
            return d

    for d in sorted_directions:
        if d in safe_moves:
            return d

    for d in sorted_directions:
        if d in future_maybe_safe_moves:
            return d

    for d in sorted_directions:
        if d in maybe_safe_moves:
            return d

    # random if no safe direction
    return random.choice(["w", "d", "s", "a"])

transverse = True

async def agent_loop(server_address="localhost:8000", agent_name="student"):
    global count, directions_priorities, zigzag_row, range_dif_count, obstacles, search_moves, transverse, directions_cost
    async with websockets.connect(f"ws://{server_address}/player") as websocket:
        await websocket.send(json.dumps({"cmd": "join", "name": agent_name}))

        search_moves = []
        current_aim = []
        forbidden, terrible, worse, bad, possible, good, important = 1,2,3,5,8,13,21
        obstacles = set()  # Collect obstacles for A* search

        while True:
            try:
                state = json.loads(await websocket.recv())
                directions_priorities = {"w":[8,0], "d":[8,0], "s":[8,0], "a":[8,0]}
                directions_cost = {"w":0, "d":0, "s":0, "a":0}
                listDir = ["w", "d", "s", "a"]
                if state.get("body", []):
                    snake = state["body"]
                    sight = state["sight"]
                    global transverse
                    transverse = state["traverse"]
                    steps = state["step"]

                    sight_control(snake, sight, steps)
                    def_limits(snake, current_aim, state["range"])
                    def_body_limits(snake)

                    if range_dif_count[0] != 0:
                        if range_dif_count[1] != None:
                            order_directions_priorities(range_dif_count[1], 13)
                            range_dif_count[1] = None
                    else:
                        range_dif_count[0] = state["range"] * 2
                        range_dif_count[1] = None
                    field_of_view = state["sight"]

                    if len(search_moves) != 0:
                        order_directions_priorities(search_moves.pop(0),21)
                    change_row(snake)
                if state.get("body", []):
                    snake = state["body"]
                    sight = state["sight"]
                    current_aim = choose_direction(snake, sight)
                    # This should never happen, but if it does random play and pray
                    if len(current_aim) == 0:
                        current_aim = random.choice(listDir)

                direction = {"cmd": "key", "key": current_aim}

                await websocket.send(json.dumps(direction))

            except websockets.exceptions.ConnectionClosedOK:
                print("Server has cleanly disconnected us")
                return

loop = asyncio.get_event_loop()
SERVER = os.environ.get("SERVER", "localhost")
PORT = os.environ.get("PORT", "8000")
NAME = os.environ.get("NAME", "shaq")
loop.run_until_complete(agent_loop(f"{SERVER}:{PORT}", NAME))