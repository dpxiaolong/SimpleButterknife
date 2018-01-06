package injectorFrame;

import android.app.Activity;
import android.util.Log;
import android.view.View;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 * 注入类，bind方法中扩展，
 * 支持对view注入id完成findviewByid
 * 支持对方法注入id完成setOnclick
 *
 * Created by luozhenlong on 2018/1/6.
 */

public class SimpleButterKnife {

    private static final String TAG = "SimpleButterKnife";

    /**
     * 绑定id 到activity view类型的方法
     *
     * @param activity
     */
    public static void bind(final Activity activity) {
        //获得activity的类型类
        Class<? extends Activity> clazz = activity.getClass();
        //获得所有activity的public变量
        Field[] fields = clazz.getFields();
        Log.i(TAG, "fields size" + fields.length);

        injectView(activity, fields);
        injectString(activity, fields);
        //获得所有方法，注入
        Method[] methods = clazz.getMethods();
        injectOnclick(activity, methods);
        injectOnlongClick(activity, methods);

    }
    //注入String的方法
    public static  void injectString(Activity activity, Field[] fields){
        for (Field field : fields) {
            //field需要过滤掉没有BindString注解的
            BindString bindString = field.getAnnotation(BindString.class);

            if (bindString != null) {
                Log.i(TAG, "field:" + field.getName() + " getGenericType:" + field.getGenericType());
                int id = bindString.value();
                if (id != -1) {
                    String result = activity.getString(id);
                    try {
                        field.set(activity, result);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    //注入id到View
    public static void injectView(Activity activity, Field[] fields) {

        for (Field field : fields) {
            //field需要过滤掉没有BindView注解的
            BindView bindView = field.getAnnotation(BindView.class);

            if (bindView != null) {
                Log.i(TAG, "field:" + field.getName() + " getGenericType:" + field.getGenericType());
                int id = bindView.value();
                if (id != -1) {
                    View view = activity.findViewById(id);
                    try {
                        field.set(activity, view);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * 注入绑定onclick事件
     *
     * @param activity
     * @param methods
     */
    public static void injectOnclick(final Activity activity, Method[] methods) {

        for (final Method method : methods) {
            BindOnclick bindOnclick = method.getAnnotation(BindOnclick.class);
            //如果方法上有BindOnclick注解才处理
            if (null != bindOnclick) {
                Log.i(TAG, "method.getName:" + method.getName());
                int ids[] = bindOnclick.value();
                for (int id : ids) {
                    //id有效时
                    if (id != -1) {
                        View view = activity.findViewById(id);
                        if (view != null) {
                            view.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    try {
                                        method.invoke(activity, v);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }
                    }

                }
            }
        }

    }

    public static void injectOnlongClick(final Activity activity, Method[] methods){


        for (final Method method : methods) {
            BindOnlongClick bindOnlongClick = method.getAnnotation(BindOnlongClick.class);
            //如果方法上有BindOnclick注解才处理
            if (null != bindOnlongClick) {
                Log.i(TAG, "injectOnlongClick method.getName:" + method.getName());
                int ids[] = bindOnlongClick.value();
                for (int id : ids) {
                    //id有效时
                    if (id != -1) {
                        View view = activity.findViewById(id);
                        if (view != null) {
                            view.setOnLongClickListener(new View.OnLongClickListener() {
                                @Override
                                public boolean onLongClick(View v) {
                                    try {
                                        Log.i(TAG,"onLongClick");
                                        method.invoke(activity, v);
                                        return true;
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    return false;

                                }
                            });
                        }
                    }

                }
            }
        }


    }
}
