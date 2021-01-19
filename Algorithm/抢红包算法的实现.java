import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Random;

class Packet {
    // 总金额
    private int totalAmount;
    // 总个数
    private int totalCount;
    public Packet(int totalAmount, int totalCount) {
        assert totalAmount > 0;
        assert totalCount > 0;
        this.totalAmount = totalAmount;
        this.totalCount = totalCount;
    }
    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
}

public class Main {
    public static void main(String[] args) {
        Random random = new Random();
        for(int x = 0 ;x < 100 ;x ++){
            test(random.nextInt(1000), random.nextInt(20) + 1);
        }
    }

    public static void test(int totalAmount, int totalCount){

        BigDecimal bigDecimal = new BigDecimal(0);

        Packet packet = new Packet(totalAmount, totalCount);

        System.out.print("[");
        for (int x = 0 ;x < totalCount ;x ++){
            int value = unpacking(packet);
            bigDecimal = bigDecimal.add(new BigDecimal(value));
            if (x != 0){
                System.out.print(", ");
            }
            System.out.print(value);
        }

        System.out.println("] totalAmount=" + totalAmount + ",totalCount=" + totalCount+ ", accuracy=" + (bigDecimal.intValue() == totalAmount));
    }

    // 打开一个红包
    public static int unpacking(Packet packet){

        if (packet.getTotalCount() == 1){
            // 最后一个红包
            int totalAmount =  packet.getTotalAmount();
            packet.setTotalAmount(0);
            packet.setTotalCount(0);
            return totalAmount;
        }else if(packet.getTotalCount() == 0){
            // 红包已经领取完
            return 0;
        }

        // 最大金额 = (总金额 / 总个数)(四舍五入掉小数) * 2
        BigDecimal totalAmount = new BigDecimal(packet.getTotalAmount());
        BigDecimal maxValue = totalAmount.divide(new BigDecimal(packet.getTotalCount()),0 , RoundingMode.HALF_UP).multiply(new BigDecimal(2));

        // 0% - 100% 随机概率
        BigDecimal random = new BigDecimal(new Random().nextInt(101)).divide(new BigDecimal(100),2, RoundingMode.UNNECESSARY);

        // 随机获取金额(四舍五入掉小数位)
        int randomValue = maxValue.multiply(random, MathContext.UNLIMITED).intValue();

        // 最小可以获取到1分
        randomValue = randomValue < 1 ? 1 : randomValue;

        // 修改红包数据
        packet.setTotalAmount(packet.getTotalAmount() - randomValue);
        packet.setTotalCount(packet.getTotalCount() - 1);

        return randomValue;
    }
}
