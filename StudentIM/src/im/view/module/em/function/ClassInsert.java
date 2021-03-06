package im.view.module.em.function;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import im.entity.Field;
import im.entity.Record;
import im.util.Config;
import im.util.view.Img;
import im.view.module.IMButton;
import im.view.module.EditModule;
import im.view.module.Module;
import im.view.module.TableModule;
import im.view.module.em.EM;

public class ClassInsert extends IMButton{
	/**
	 * 新增班级
	 */
	private static final long serialVersionUID = 8299018064782580715L;
	private EM em=null;
	private Module edit=null;
	private Record record=null;
	private static ClassInsert function=new ClassInsert();
	private boolean inLoad=false;
	private ClassInsert(){
		/**
		 * 采用单例模式，不需要通过构造实例化，所以设为private
		 */
	}
	private ClassInsert(EM em){
		setEM(em);
		
	}
	public void setEM(EM em){
		this.em=em;
		init();
	}
	public static Module getFun(EM em){
		function.setEM(em);
		return function;
	}
	public void init(){
		super.init();
		this.setSize(EM.ICON_WIDTH,EM.ICON_HEIGHT);
		this.setBackground(Color.BLACK);
		this.add(Img.getImg("sources/img/classInsert.png", 0
				, 0,EM.ICON_WIDTH, EM.ICON_HEIGHT));
		this.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e){
				if(!inLoad){
					load();
				}
			}
		});
		
	}
	private void loadEdit(){
		insert();
		edit=new Module("edit");
		edit.setBounds(0, 0, EM.WIDTH, EM.EDIT_HEIGHT);
		edit.setBackground(Color.WHITE);
		if(record.getField("id").getValue()!=null&&
				(!record.getField("id").getValue().equals(""))){
			edit.add(new ClassInfo());
		}
		edit.repaint();
	}
	@Override
	public void load(){
		inLoad=true;
		if(edit==null){
			loadEdit();
		}else{
			insert();
			edit.removeAll();
			if(record.getField("id").getValue()!=null&&
					(!record.getField("id").getValue().equals(""))){
				edit.add(new ClassInfo());
			}
		}
		em.getEditModule().removeAll();
		em.getEditModule().add(edit);
		em.getEditModule().repaint();
	}
	private void insert(){
		setRecord();
		new EditModule(record,0);
		new Thread(new Runnable(){
			@Override
			public void run() {
				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				inLoad=false;
			}
		}).start();
	}
	private void setRecord(){
		List<String> info=Config.getDOM().getField("class");
		Map<String,String> mean=Config.getDOM().getFieldName("class");
		Field[] field=new Field[info.size()];
		for(int i=0,n=info.size();i<n;i++){
			field[i]=new Field();
			field[i].setFieldName(info.get(i));
			field[i].setMean(mean.get(info.get(i)));
		}
		record=new Record(field);
		record.setTable("class");
	}
	private class ClassInfo extends Module{
		/**
		 * 刚插入的班级信息
		 */
		private static final long serialVersionUID = -1536821918995067372L;
		private List<Record> list=null;
		private TableModule table=null;
		public ClassInfo(){
			init();
		}
		private void init(){
			this.setBounds(0,0,edit.getWidth(),edit.getHeight());
			this.setBackground(Color.WHITE);
			loadTable();
		}
		private void loadTable(){
			if(table!=null){
				this.remove(table);
			}
			list=new ArrayList<>();
			list.add(record);
			table=new TableModule(list);
			int xtemp=this.getWidth()-table.getWidth()>>1;
			table.setLocation(xtemp, 100);
			this.add(table);
			this.repaint();
		}
	}
}
