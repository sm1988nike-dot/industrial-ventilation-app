package com.example.vpcalculator;
import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.content.*;
import android.text.*;
import android.view.View;
import android.widget.*;
import java.util.Locale;
public class MainActivity extends Activity {
 private static final double FACTOR=4.043*4.043;
 private EditText before,after;
 private TextView first,second,change;
 private boolean solveV=true;
 private int dp(int v){return (int)(v*getResources().getDisplayMetrics().density+0.5f);}
 private TextView label(String s,int size){TextView t=new TextView(this);t.setText(s);t.setTextSize(size);t.setTextColor(Color.rgb(30,40,55));t.setPadding(0,dp(8),0,dp(8));return t;}
 private String fmt(double n){return String.format(Locale.KOREA,"%,.6f",n).replaceAll("0+$","").replaceAll("\\.$","");}
 @Override public void onCreate(Bundle b){super.onCreate(b);
 ScrollView scroll=new ScrollView(this);LinearLayout root=new LinearLayout(this);root.setOrientation(1);root.setPadding(dp(24),dp(30),dp(24),dp(30));scroll.addView(root);setContentView(scroll);
 root.addView(label("V–P 전후 비교 계산기",25));root.addView(label("V = 4.043√(VP)",21));root.addView(label("양수 V: V = 16.345849 × P",14));
 RadioGroup group=new RadioGroup(this);group.setOrientation(LinearLayout.HORIZONTAL);
 RadioButton v=new RadioButton(this),p=new RadioButton(this);v.setId(View.generateViewId());p.setId(View.generateViewId());v.setText("V 구하기");p.setText("P 구하기");group.addView(v);group.addView(p);root.addView(group);
 root.addView(label("변경 전",20));before=new EditText(this);before.setSingleLine(true);before.setInputType(8194);before.setHint("P 값 입력");root.addView(before);first=label("변경 전 V: —",19);root.addView(first);
 root.addView(label("변경 후",20));after=new EditText(this);after.setSingleLine(true);after.setInputType(8194);after.setHint("P 값 입력");root.addView(after);second=label("변경 후 V: —",19);root.addView(second);change=label("차이: —",19);root.addView(change);
 Button copy=new Button(this);copy.setText("결과 복사");root.addView(copy);Button reset=new Button(this);reset.setText("초기화");root.addView(reset);
 TextWatcher watcher=new TextWatcher(){public void beforeTextChanged(CharSequence s,int start,int count,int a){}public void onTextChanged(CharSequence s,int start,int before,int count){update();}public void afterTextChanged(Editable e){}};
 before.addTextChangedListener(watcher);after.addTextChangedListener(watcher);
 group.setOnCheckedChangeListener((g,id)->{solveV=id==v.getId();before.setHint(solveV?"P 값 입력":"V 값 입력");after.setHint(solveV?"P 값 입력":"V 값 입력");update();});v.setChecked(true);
 copy.setOnClickListener(w->{String s=first.getText()+"\n"+second.getText()+"\n"+change.getText();((ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText("VP 결과",s));Toast.makeText(this,"복사했습니다",Toast.LENGTH_SHORT).show();});
 reset.setOnClickListener(w->{before.setText("");after.setText("");});
 update();
 }
 private double calc(EditText e){try{String s=e.getText().toString().trim();if(s.isEmpty())return Double.NaN;double x=Double.parseDouble(s);if(!Double.isFinite(x)||x<0)return Double.NaN;return solveV?FACTOR*x:x/FACTOR;}catch(Exception ex){return Double.NaN;}}
 private void update(){if(before==null||after==null)return;double a=calc(before),b=calc(after);String k=solveV?"V":"P";first.setText("변경 전 "+k+": "+(Double.isFinite(a)?fmt(a):"—"));second.setText("변경 후 "+k+": "+(Double.isFinite(b)?fmt(b):"—"));if(Double.isFinite(a)&&Double.isFinite(b)){double d=b-a;change.setText("차이: "+fmt(d)+(a==0?" (변화율 계산 불가)":" / 변화율: "+fmt(d/a*100)+"%"));}else change.setText("차이: —");}
}