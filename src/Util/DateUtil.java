package Util;

public class DateUtil {
	//java.sql.Timestamp date time 都是java.util.Date的子类 可以删除这个方法
    public static java.sql.Timestamp dateToTimestamp(java.util.Date d){
    	if(d==null) {
    		return null;
    	}
    	return new java.sql.Timestamp(d.getTime());
    }
    
    public static java.util.Date TimestampToDate(java.sql.Timestamp t){
    	if(t==null) {
    		return null;
    	}
    	return new java.util.Date(t.getTime());
    }
}
