





主要功能

1.简化findViewById(int id);
    1.activity中的使用
        1.以前
        TextView textView=findViewById(R.id.textView);
        2.现在
        @InjectView(R.id.textView);
        TextView textView;
        在setContentView(view)后面调用
        ButterKnife.inject(this);

        注意:View变量申明的时候不能为public或者static
    2.Fragment中的的使用
        1.现在
        View view=inflater.inflater(R.layout.fragment_simple,container,false);
        ButterKnife.inject(this,view);
        @InjectView(R.id.textView);
        TextView textView;
    3.在viewholder中的使用
    static class ViewHolder{
        @InjectView(R.id.name);
        TextView name;
        public ViewHolder(View view){
            ButterKnife.inject(this,view);
        }
    }
    4.省略setOnClickListener
        1.以前
        button.setOnClickListener(new View.OnClickListener(){
               @Oerride
               public void onClick(View view){
                   finish();
               } 
        });
        2.现在
        @OnClick(R.id.button)
        void finishA(View view){
            finish();
        }
        ButterKnife.inject(this); 
        
        注意:这里方法仍然不能为private和static,并且可以有一个参数view,也可以不写

    5.ListView的点击@OnItemClick,CheckBox的@OnCheckedChange也可以实现省略操作   

    6.可以一次指定多个id,为多个view绑定一个事件处理方法
    @OnClick(
        {
            R.id.button,R.id.person,R.id.mode
        }
    )
    void viewClick(View view){
        Toast.make(this,"you click the button",Toast.LENGTH_SHORT).show();
    }


    这里有一个快捷键 
    如果是在activity里面使用alt+insert 在弹出菜单里面点击 Generate ButterKnife Injections