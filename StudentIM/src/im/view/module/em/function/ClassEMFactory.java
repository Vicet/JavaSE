package im.view.module.em.function;

import im.view.module.Module;
import im.view.module.em.EM;

public class ClassEMFactory {
	public static Module getFunction(int index,EM em){
		switch(index){
			case 0:
				return ClassStudent.getFun(em);
			case 1:return ClassInfo.getFun(em);
			case 2:return ClassInsert.getFun(em);
			case 3:
			case 4:
			case 5:
			default:return null;
		}
	}
}
