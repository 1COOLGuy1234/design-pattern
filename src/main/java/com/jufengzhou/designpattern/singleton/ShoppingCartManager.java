package com.jufengzhou.designpattern.singleton;

import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

public class ShoppingCartManager implements ShoppingCart {

    private Map<String, Integer> db;

    private static ShoppingCartManager instance;

    private ShoppingCartManager() {
        db = new ConcurrentHashMap<>();
    }


    public static synchronized ShoppingCartManager getInstance() {
        if (instance == null) {
            instance = new ShoppingCartManager();
        }
        return instance;
    }

    public void addToCart(String goodName, Integer size) {
        db.merge(goodName, size, Integer::sum);
    }

    public Integer query(String goodName) {
        return db.get(goodName);
    }

    public String viewCart() {
        StringBuilder sb = new StringBuilder();
        for (String key : db.keySet()) {
            sb.append(key).append(" ").append(db.get(key)).append("\n");
        }
        return sb.toString();
    }


    public static void main(String[] args) {
        ShoppingCartManager cart = ShoppingCartManager.getInstance();
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNext()) {
            String itemName = scanner.next();
            int quantity = scanner.nextInt();

            // 获取购物车实例并添加商品
            cart.addToCart(itemName, quantity);
        }

        // 输出购物车内容
        System.out.println(cart.viewCart());

        scanner.close();
    }

}
