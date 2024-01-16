# 機會損失 change loss 是指
# 要使用 Python 創建一個簡單的機會損失系統來管理午餐便當的銷售，考慮幾個基本元素：便當種類、價格、數量，以及銷售紀錄。
# 在商業和經濟學中，機會損失通常指因為資源（如便當的數量）不足而無法滿足需求時所失去的潛在收益。
# 初始化便當數據
import random
import time
import matplotlib.pyplot as plt
import matplotlib

# 設置支持中文的字體，例如使用 Microsoft YaHei
matplotlib.rcParams['font.family'] = 'Microsoft YaHei'
matplotlib.rcParams['font.size'] = 10

# 全局變數定義
MIN_BUY = 1  # 最小購買數量
MAX_BUY = 10  # 最大購買數量
THRESHOLD = 0.6  # 低於 N % 進行補貨
RESTOCK_AMOUNT = MAX_BUY * 1  # 補貨數量
TX_AMOUNT = 30  # 交易次數
TX_DELAY = 0.5  # 交易延遲時間

bentos = {
    '雞腿便當': {'price': 100, 'quantity': 50, 'missed_sales': 0},
    '素食便當': {'price': 80, 'quantity': 30, 'missed_sales': 0}
}

def sell_bento(name, quantity):
    if name not in bentos:
        print("找不到指定的便當種類。")
        return

    bento = bentos[name]
    if quantity > bento['quantity']:
        missed = quantity - bento['quantity']
        bento['missed_sales'] += missed
        bento['quantity'] = 0
        print(f"庫存不足，只能銷售 {quantity - missed} 個 {name}。")
    else:
        bento['quantity'] -= quantity
        print(f"已售出 {quantity} 個 {name}。")

def check_and_restock(name):
    global THRESHOLD, RESTOCK_AMOUNT  # 使用全局變數
    bento = bentos[name]
    if bento['quantity'] < THRESHOLD * (bento['quantity'] + bento['missed_sales']):
        bento['quantity'] += RESTOCK_AMOUNT
        print(f"{name} 的庫存低於 15%，增加了 {RESTOCK_AMOUNT} 個。")

def calculate_opportunity_loss():
    total_loss = 0
    for name, bento in bentos.items():
        loss = bento['missed_sales'] * bento['price']
        total_loss += loss
        print(f"{name} 因庫存不足而失去的機會損失: {loss}")
    return total_loss

def draw_loss_chart(loss_history, iteration):
    plt.pause(0.001)
    plt.clf()  # 清除當前圖表
    cumulative_loss = {name: sum(losses) for name, losses in loss_history.items()}
    total_loss = sum(cumulative_loss.values())

    for name in loss_history:
        plt.plot(loss_history[name], label=f'{name} 損失')

    plt.xlabel('交易次數')
    plt.ylabel('機會損失')
    plt.title(f'當庫存低於 {THRESHOLD*100}% 補貨 {RESTOCK_AMOUNT} 個便當\n交易 {iteration} 後的機會損失 - 累計損失: {total_loss}')
    plt.legend()
    plt.draw()

    
# 主程式
def main():
    i = 0
    loss_history = {'雞腿便當': [], '素食便當': []}

    while i < TX_AMOUNT:  # 限制迴圈次數以便於繪圖展示
        i += 1
        print("交易次數:", i)
        for name in bentos:
            print(f"{name}-目前庫存: {bentos[name]['quantity']}")
            check_and_restock(name)

        bento_name = random.choice(list(bentos.keys()))
        quantity = random.randint(MIN_BUY, MAX_BUY)
        sell_bento(bento_name, quantity)

        time.sleep(TX_DELAY)

        for name in bentos:
            loss = bentos[name]['missed_sales'] * bentos[name]['price']
            loss_history[name].append(loss)
            print(f"{name} 因庫存不足而失去的機會損失: {loss}")

        draw_loss_chart(loss_history, i)
        time.sleep(TX_DELAY)

if __name__ == "__main__":
    plt.ion()  # 開啟交互模式
    main()
    # 等待使用者關閉視窗
    plt.ioff()
    plt.show()
    