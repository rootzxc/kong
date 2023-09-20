package com.itheima.ui;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class GameJFrame extends JFrame implements KeyListener , ActionListener {
    //JFrame 界面  窗体
    //子类呢？也表示界面 窗体
    //规定：GameJFrame这个界面表示的就是游戏的主界面
    //以后跟游戏相关的所有逻辑都写在这个类中

    //4.创建一个二维数据   目的：用来管理数据   加载图片的时候，会根据二维数组中的数据进行加载
    int[][] data=new int[4][4];
    //记录方块再二维数组中的位置
    int x=0;
    int y=0;

    //定义一个二维数组，存储正确的数据
    int[][] win={
            {1,2,3,4},
            {5,6,7,8},
            {9,10,11,12},
            {13,14,15,0},
    };

    //定义变量用来统计步数
    int step=0;

    //创建选项下面的条目对象
    JMenuItem replayItem=new JMenuItem("重新游戏");
    JMenuItem reLoginItem=new JMenuItem("重新登录");
    JMenuItem closeItem=new JMenuItem("关闭游戏");

    JMenuItem accountItem=new JMenuItem("公众号");

    public GameJFrame(){
        //初始化界面
        initJFrame();

        //初始化菜单
        initJMenuBar();

        //初始化数据（打乱）
        initData();

        //初始化图片
        initImage();


        //让界面显示出来
        this.setVisible(true);
    }


    private void initData() {
        //1.定义一个一维数组
        int[] tempArr = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
        //打乱数组中数据的顺序
        //遍历数组，得到每一个元素，拿着每一个元素跟随机索引上的数据进行交换
        Random r=new Random();
        for (int i = 0; i < tempArr.length; i++) {
            //获取随机索引
            int index=r.nextInt(tempArr.length);
            //拿着遍历到的每一个数据，跟随机索引上的数据进行交换
            int temp=tempArr[i];
            tempArr[i]=tempArr[index];
            tempArr[index]=temp;
        }



        //5.给二维数组添加数据
        //解法一：遍历一维数组tempArr得到每一个元素，把每一个元素依次添加到二维数组当中
        for (int i = 0; i < tempArr.length; i++) {
            if (tempArr[i]==0){
                x=i/4;
                y=i%4;
            }
                data[i/4][i%4]=tempArr[i];

        }

    }

    //初始化图片
    //添加图片的时候，就需要按照二维数组中管理的数据添加图片
    private void initImage() {
        //清空原本已经出现的所有图片
        this.getContentPane().removeAll();
        int number=1;

        if (victory()){
            //显示胜利的图标
            JLabel winJlabel =new JLabel(new ImageIcon("E:\\puzzlegame\\com.itheima.ui\\image\\win.png"));
            winJlabel.setBounds(203,283,197,73);
            this.getContentPane().add(winJlabel);
        }

        JLabel stepCount=new JLabel("步数："+step);
        stepCount.setBounds(50,30,100,20);
        this.getContentPane().add(stepCount);


        //外循环---把内循环重复执行了4次
        for (int i = 0; i < 4; i++) {
            //内循环---表示在一行添加4张图片
            for (int j = 0; j < 4; j++) {
                //获取当前要加载图片的序号
                int num=data[i][j];
                //创建一个JLabel对象(管理容器)
                JLabel jLabel1=new JLabel(new ImageIcon("E:\\puzzlegame\\com.itheima.ui\\image\\animal\\animal3\\"+num+".jpg"));
                //指定图片位置
                jLabel1.setBounds(105*j+83,105*i+134,105,105);
                //给图片添加边框
                jLabel1.setBorder(new BevelBorder(1));
                //把管理容器添加到界面
                //this.add(jLabel1);
                this.getContentPane().add(jLabel1);

            }

        }
        //添加背景图片
        JLabel background=new JLabel(new ImageIcon("E:\\puzzlegame\\com.itheima.ui\\image\\background.png"));
        background.setBounds(40,40,508,560);
        //把背景图片添加到界面当中
        this.getContentPane().add(background);

        //刷新一下界面
        this.getContentPane().repaint();

    }

    private void initJMenuBar() {
        //创建整个的菜单对象
        JMenuBar jMenuBar=new JMenuBar();

        //创建菜单上面的两个选项的对象（功能  关于我们）
        JMenu functionJMenu=new JMenu("功能");
        JMenu aboutJMenu=new JMenu("关于我们");



        //将每一个选项下面的条目对象添加到选项当中
        functionJMenu.add(replayItem);
        functionJMenu.add(reLoginItem);
        functionJMenu.add(closeItem);

        aboutJMenu.add(accountItem);

        //给条目绑定事件
        replayItem.addActionListener(this);
        reLoginItem.addActionListener(this);
        closeItem.addActionListener(this);
        accountItem.addActionListener(this);

        //将菜单里面的两个选项添加到菜单当中
        jMenuBar.add(functionJMenu);
        jMenuBar.add(aboutJMenu);

        //给整个界面设置菜单
        this.setJMenuBar(jMenuBar);
    }

    private void initJFrame() {
        //设置界面的宽高
        this.setSize(603,680);
        //设置界面的标题
        this.setTitle("拼图单机版 v1.0");
        //设置界面置顶
        this.setAlwaysOnTop(true);
        //设置界面居中
        this.setLocationRelativeTo(null);
        //设置关闭模式
        this.setDefaultCloseOperation(3);

        //取消默认的居中放置，只有取消了才会按照XY轴的形式添加组件
        this.setLayout(null);
        //给整个界面添加键盘监听事件
        this.addKeyListener(this);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    //按下不松时会调用这个方法
    @Override
    public void keyPressed(KeyEvent e) {
        int code=e.getKeyCode();
        if (code==65){
            //把界面中所有的图片全部删除
            this.getContentPane().removeAll();
            //加载第一张完整的图片
            JLabel all=new JLabel(new ImageIcon("E:\\puzzlegame\\com.itheima.ui\\image\\animal\\animal3\\all.jpg"));
            all.setBounds(83,134,420,420);
            this.getContentPane().add(all);
            //加载背景图片
            //添加背景图片
            JLabel background=new JLabel(new ImageIcon("E:\\puzzlegame\\com.itheima.ui\\image\\background.png"));
            background.setBounds(40,40,508,560);
            //把背景图片添加到界面当中
            this.getContentPane().add(background);
            //刷新一下界面
            this.getContentPane().repaint();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        //判断游戏是否胜利，如果胜利，此方法需要直接结束，不能再执行下面的移动代码了
        if (victory()){
            //结束方法
            return;
        }


        //对上下左右进行判断
        //左37 上38 右39 下40
        int code=e.getKeyCode();
        if (code==37){
            System.out.println("向左移动");
            if (y==3){
                //表示空白方块已经在最右方了，他的右方没有图片再能移动了
                return;
            }
            data[x][y]=data[x][y+1];
            data[x][y+1]=0;
            y++;
            //每移动一次，计数器就自增一次
            step++;
            //调用方法按照最新的数字加载图片
            initImage();
        }else if(code==38){
            System.out.println("向上移动");
            if (x==3){
                //表示空白方块已经在最下方了，他的下方没有图片再能移动了
                return;
            }
            //逻辑：把空白方块下方的数字往上移动
            //把空白方块下方的数字赋值给空白方块
            data[x][y]=data[x+1][y];
            data[x+1][y]=0;
            x++;
            //每移动一次，计数器就自增一次
            step++;
            //调用方法按照最新的数字加载图片
            initImage();
        }else if(code==39){
            System.out.println("向右移动");
            if (y==0){
                //表示空白方块已经在最左方了，他的左方没有图片再能移动了
                return;
            }
            data[x][y]=data[x][y-1];
            data[x][y-1]=0;
            y--;
            //每移动一次，计数器就自增一次
            step++;
            //调用方法按照最新的数字加载图片
            initImage();
        }else if(code==40){
            System.out.println("向下移动");
            if (x==0){
                //表示空白方块已经在最上方了，他的上方没有图片再能移动了
                return;
            }
            data[x][y]=data[x-1][y];
            data[x-1][y]=0;
            x--;
            //每移动一次，计数器就自增一次
            step++;
            //调用方法按照最新的数字加载图片
            initImage();
        }else if(code==65){
            initImage();
        }else if (code==87){
            data=new int[][]{
                    {1,2,3,4},
                    {5,6,7,8},
                    {9,10,11,12},
                    {13,14,15,0}
            };
            initImage();
        }



    }


    //判断data数组中的数组是否跟win数组中相同
    //如果全部相同，返回true 否则返回false
    public boolean victory(){
        for (int i = 0; i < data.length; i++) {
            //i:依次表示二维数组   data里面的索引
            //data[i]:依次表示每一个一维数组
            for (int j = 0; j < data[i].length; j++) {
                if (data[i][j]!=win[i][j]){
                    //只要有一个数据不一样，则返回false
                    return false;
                }
            }
        }
        //循环结束表示数组遍历比较完毕，全部一样返回true
        return true;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //获取当前被点击的条目对象
        Object obj=e.getSource();
        //判断
        if (obj==replayItem){
            System.out.println("重新游戏");
            //计步器清零
            step=0;
            //再次打乱二维数组中的数据
            initData();
            //重新加载图片
            initImage();

        }else if (obj==reLoginItem){
            System.out.println("重新登录");
            //关闭当前的游戏界面
            this.setVisible(false);
            //打开登陆界面
            new LoginJFrame();
        }else if (obj==closeItem){
            System.out.println("关闭游戏");
            //直接关闭虚拟机即可
            System.exit(0);
        }else if (obj==accountItem){
            System.out.println("公众号");
            //创建一个弹框对象
            JDialog jDialog=new JDialog();
            //创建一个管理图片的容器对象
            JLabel jLabel=new JLabel(new ImageIcon("E:\\puzzlegame\\com.itheima.ui\\image\\about.png"));
            //设置位置和宽高
            jLabel.setBounds(0,0,258,258);
            //把图片添加到弹框中
            jDialog.getContentPane().add(jLabel);
            //给弹框设置大小
            jDialog.setSize(344,344);
            //让弹框置顶
            jDialog.setAlwaysOnTop(true);
            //让弹框居中
            jDialog.setLocationRelativeTo(null);
            //弹框不关闭则无法操作下面的界面
            jDialog.setModal(true);
            //让弹框显示出来
            jDialog.setVisible(true);
        }
    }
}
