package com.ljljob.concurrent.struct;

import java.util.Iterator;

/**
 * @Author: wujianmin
 * @Date: 2019/10/25 10:27
 * @Function:
 * @Version 1.0
 */
public class MyLinkedList<T> {
    private Node<T> first;//指向第一个节点的工作指针
    private Node<T> last;//指向最后一个节点的工作指针
    private int size=0;//统计链表长度
    private static class Node<T>{
        T data;
        Node<T> next;
    }
    //获取长度
    public int size(){
        return size;
    }
    //添加节点
    public void add(T data){
        Node<T> newNode=new Node<T>();
        newNode.data=data;

        if(first==null){
            first=newNode;
            last =newNode;
            size++;
            return;
        }
        last.next=newNode;
        last =newNode;
        size++;
    }
    //插入第一个位置
    public void addFirst(T data){
        Node<T> newNode=new Node<T>();
        newNode.data=data;

        if(first==null){
            first=newNode;
            last =newNode;
            size++;
            return;
        }
        newNode.next=first;
        first=newNode;
        size++;
    }
    public void addLast(T data){
        add(data);
    }
    //根据索引添加节点
    public void add(int index,T data){
        Node<T> newNode=new Node<T>();
        newNode.data=data;

        Node node=getNode(index);//指向指定索引之前的节点

        if(node==null){
            addFirst(data);
            return;
        }
        newNode.next=node.next;
        node.next=newNode;
        size++;
    }
    /**
     * 得到指定索引的数据
     * @param index
     * @return 数据
     */
    public T get(int index){
        return getNode(index).next.data;
    }
    /**
     * 得到指定索引的前一个节点
     * @param index
     * @return
     */
    private Node<T> getNode(int index) {
        Node<T> node=null;
        if(index==0){
            //插入到第一个
            return null;
        }
        if(index<size){
            //插入中间
            node=first;
            for(int i=1;i<index;i++){
                node=node.next;
            }
            return node;
        }
        return last;
    }
    /**
     * 迭代器
     */
    public Iterator<T> iterator(){
        return new Iterator<T>() {
            Node<T> cur;
            @Override
            public boolean hasNext() {
                if(cur==null && first!=null){
                    cur=first;
                    return true;
                }
                if(cur.next!=null){
                    cur=cur.next;
                    return true;
                }
                return false;
            }
            @Override
            public T next() {
                return cur.data;
            }
        };
    }
    /**
     * 移除数据
     * @param data
     */
    public void remove(T data){
        //查找数据的索引
        int index=getIndex(data);
        remove(index);
    }
    /**
     * 通过数据查找第一次出现的索引
     * @param data
     * @return
     */
    private int getIndex(T data) {
        Node node=first;
        for(int i=0;i<size;i++){
            if(node.data.equals(data)){
                return i;
            }
            node=node.next;
        }
        return -1;
    }
    /**
     * 移除指定索引的数据
     * @param index
     */
    public void remove(int index){
        Node prev=getNode(index);
        Node node=getNode(index+1);
        prev.next=node.next;
        size--;
    }
    /**
     * 移除第一个
     */
    public void removeFirst(){
        first=first.next;
        size--;
    }
    /**
     * 移除最后一个
     */
    public void removeLast(){
        //查找倒数第二个节点
        Node node=getNode(size-1);
        node.next=null;
        last =node;
        size--;
    }
    /**
     * 修改指定索引的数据
     * @param index
     * @param newData
     */
    public void set(int index,T newData){
        //得到指定索引节点
        Node node=getNode(index+1);
        if(node==null){
            node=first;
        }
        //修改节点上的数据
        node.data=newData;
    }


    public static void main(String[] args) {
        MyLinkedList list = new MyLinkedList();
    }
}
