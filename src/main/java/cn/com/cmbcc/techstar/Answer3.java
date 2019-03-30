package cn.com.cmbcc.techstar;

public class Answer3 {
    Child firstChild = null;
    Child temp=null;
    int len = 0;
    int start = 0;
    int step = 0;

    /**
     * 设置链表的大小
     */
    public void setLen(int len) {
        this.len = len;
    }
    /**
     * 设置从第几个人开始数数
     */
    public void setStart(int start) {
        this.start = start;
    }
    /**
     * 设置step
     */
    public void setStep(int step) {
        this.step = step;
    }

    /**
     * 开始
     */
    public void play() {

        Child temp = this.firstChild;

        for(int i=1; i<=start-1; i++) {
            temp = temp.nextChild;
        }
        int tmpstep=step;
        while(this.len!=1) {
            // 数m下
            for (int i = 1; i < tmpstep; i++) {
                temp = temp.nextChild;
            }
            Child temp2 = temp;
            while (temp2.nextChild!=temp) {
                temp2 = temp2.nextChild;
            }
            temp2.nextChild = temp.nextChild;
            temp = temp.nextChild;
            this.len--;
            if(tmpstep==step){
                tmpstep=2*step-2;
            }else {
                tmpstep=step;
            }
        }

        System.out.println("最后剩下的号码:" + temp.no);
    }

    /**
     * 初始化环形链表
     */
    public void createLink() {
        for (int i=1; i<=len; i++) {
            if(i==1) {
                Child ch = new Child(i);
                this.firstChild = ch;
                this.temp = ch;
            } else {
                if(i==len) {
                    Child ch = new Child(i);
                    temp.nextChild = ch;
                    temp = ch;
                    temp.nextChild = this.firstChild;
                } else {

                    Child ch = new Child(i);
                    temp.nextChild = ch;
                    temp = ch;
                }
            }
        }
    }

    /**
     * 打印环形链表
     */
    public void show() {
        Child temp = this.firstChild;
        do{
            System.out.print(temp.no + " ");
            temp = temp.nextChild;
        } while(temp!=this.firstChild);
    }



    public static void main(String[] args){
        if(null==args||args.length!=1){
            System.out.println("输入参数有误");
        }else {
            int length=Integer.valueOf(args[0]);
            if(length<=0){
                System.out.println("请输入大于0的参数");
            }else if(length==1){
                System.out.println("当前一人被杀死，无人数保留");
            }else {
                Answer3 answer3 = new Answer3();
                answer3.setLen(Integer.valueOf(length));
                answer3.createLink();
                answer3.setStart(0);
                answer3.setStep(3);
                answer3.show();
                answer3.play();
            }
        }
    }
}


class Child{
    int no;
    Child nextChild = null;
    public Child(int no) {
        this.no = no;
    }

}
