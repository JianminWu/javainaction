package com.ljljob.classloader.chapter1;

/**
 * @Author: wujianmin
 * @Date: 2019/10/9 10:04
 * @Function: 加载->链接（验证+准备+解析）->初始化（使用前的准备）->使用->卸载
 * @Version 1.0
 */
public class ClassloaderDemo {

    public static void main(String[] args) {
        // 1.调用static常量触发对象初始化
//        System.out.println(Parent.parent_x);
//        System.out.println("=================================");
        // 2. 调用子类的静态常量会导致父类的初始化,但如果父类已经初始化之后不会再初始化
        System.out.println(Child.child_x);
        System.out.println("=================================");
        // 3. 调用static final 修饰的常量,在编译的时候直接放入常量池不会触发对象的初始化
        System.out.println(Child.child_final_static_x);
        System.out.println("=================================");
        // 4. 调用static final 修饰常量但是值是变动的,会触发对象的初始化
        System.out.println(Child.child_final_static_random_x);
        System.out.println("=================================");
        // 5. 使用引用数据不会触发对象初始化
        Child[] children = new Child[10];
        System.out.println("=================================");
        // 6.子类访问父类的常量不会导致子类的初始化
        System.out.println(Child.parent_x);
    }

}
