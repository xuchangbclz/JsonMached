package com.bclz.filterFile.core;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import com.bclz.filterFile.condition.FilterCondition;
import com.bclz.filterFile.condition.FindCondition;



public class FilterFiles {
	
	private FindCondition condition=new SimpleFilter();
	private volatile Vector<String> list = new Vector<String>();
	private AtomicInteger fileCount=new AtomicInteger(0);
	private ExecutorService threadPool=Executors.newCachedThreadPool();
	
	/**
	 * 输入文件路径，筛选合适的文件
	 * @param 文件路径
	 * @return 返回满足条件的文件名集合
	 */
	public  Vector<String> FilterFileByDir(String dir,Map<String, List<String>> filterKey,int threadNum)
	{
			
		try {
			File mydir = new File(dir);
			File[] listFiles = mydir.listFiles(new FilterCondition());
			File fdir=new File("NoMatched");
			if(!fdir.exists()) {
				fdir.mkdir();
			}else if(fdir.list().length!=0){
				deleteAll("NoMatched");
			}
			if(listFiles.length<200) {
				readFile(0,listFiles.length,listFiles,filterKey,list);
				System.out.println("All Thread Exe End...");
				System.out.println("Filter  File Num:"+fileCount);
				System.out.println("Saved directory NoMatched/...");
			}else {
				//多线程读取
				mutilpateThreadRead(threadPool,threadNum,listFiles,filterKey,list);
				while(true) {
					
					if(threadPool.isTerminated()) {
						System.out.println("All Thread Exe End...");
						System.out.println("Filter  File Num:"+fileCount);
						System.out.println("Saved directory NoMatched/...");
						break;
					}
					
				}
			}
			
		}catch(Exception e){
			System.out.println("文件读取异常:"+e);
		}
		return list;
		
		
	}
	
	private void mutilpateThreadRead(ExecutorService threadPool,int threadNum,File[] files,Map<String, List<String>> filterKey,Vector<String> list ) throws Exception {
		int len=files.length;
		if(threadNum==0) {
			readFile(0,len,files,filterKey,list);
		}else {
			int begin=0;
			int end=0;
			for(int i=1;i<=threadNum;i++) {
				
				begin=end;
				end=len*i/threadNum;
				final int a=begin;
				final int b=end;
				threadPool.submit(()->{
					try {
						readFile(a,b,files,filterKey,list);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						System.out.println("操作异常:"+e);
						threadPool.shutdown();
					}
				});
				if(i==threadNum) {
					threadPool.shutdown();
				}
			}
			
		}
		
		
	}
	
	/**
	 * 读取文件
	 * Title: readFile 
	 * Description:  
	 * @param pos 文件读取开始
	 * @param len 
	 * @param files
	 * @param filterKey
	 * @param list
	 * @throws Exception
	 */
	private void readFile(int begin,int end,File[] files,Map<String, List<String>> filterKey,Vector<String> list ) throws Exception {
		int isException=0;
		for (int i = begin; i < end; i++) {
			
			File file=files[i];
			StringBuffer sb=new StringBuffer();
			BufferedInputStream bufread=new BufferedInputStream(new FileInputStream(file));
			BufferedOutputStream buf=null;
			//标记输入流,以便复用,判断该输入流是否支持标记
//			if(!bufread.markSupported()) {
//				bufread=new BufferedInputStream(reader);
//			}
			//为了复用整个流
			bufread.mark(Integer.MAX_VALUE);
			byte[] data=new byte[1024];
			int count=0;
			while((count =bufread.read(data))!=-1) {
				sb.append(new String(data,0,count));
			}
			//重置指针位置，以供下一次使用
			bufread.reset();
			if (condition.processCondition(filterKey,sb.toString())) {
				fileCount.incrementAndGet();
				buf=new BufferedOutputStream(new FileOutputStream("NoMatched\\"+file.getName()));
				data=new byte[1024];
				while((count =bufread.read(data))!=-1) {
					buf.write(data, 0, count);
				}
				buf.close();
				list.add(file.getName());
				String path=file.getPath();
				String jsonName=path.split("___")[0]+".json";
				String jpgName=path.substring(0, path.lastIndexOf("."))+".png";
				try {
					isException=fileCopy(jsonName);
					if(isException==1) {
						continue;
					}
					isException=fileCopy(jpgName);
					if(isException==1) {
						continue;
					}
					new File(jsonName).delete();
					new File(jpgName).delete();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					System.out.println("文件损坏:"+file.getName()+"\\n"+e);
					continue;
				}
				bufread.close();
				//Java删除文件有可能出现下无法删除文件
				//一：读写文件，有没关闭的io；这里我是这种情况
				//二：被别的进程引用了要删除的文件；
				//三：依赖于此文件的对象还没有被垃圾回收（GC）机制回收，无法进行删除。  可用System.gc()测试
				file.delete();
			}
			
		}
		
		
	}
	
	/**
	 * 
	 * Title: deleteAll 
	 * Description: 递归删除所有文件及其里面的文件夹
	 * @param path
	 * @return
	 */
	public boolean deleteAll(String path) {
		Integer count =0;
		File f=new File(path);
		if(!f.exists()) {
			return false;
		}
		if(!f.isDirectory()) {
			count++;
			f.delete();
			return true;
		}
		String[] fileList=f.list();
		if(fileList.length==0) {
				f.delete();
		}
		for(String fileName:fileList) {
			if(path.endsWith(File.separator)) {
				deleteAll(path+fileName);
			}else {
				deleteAll(path+File.separator+fileName);
			}
		}
		
		return true;
		
	}

	
	public int fileCopy(String path) throws Exception {
		int isException=0;
		File file=new File(path);
		if(file.exists()) {
			BufferedInputStream bufinput=new BufferedInputStream(new FileInputStream(file));
			OutputStream out=new FileOutputStream("NoMatched\\"+file.getName());
			BufferedOutputStream buf=new BufferedOutputStream(out);
			byte[] data=new byte[1024];
			int readCount=0;
			while((readCount=bufinput.read(data))!=-1) {
				buf.write(data, 0, readCount);
			}
			bufinput.close();
			buf.close();
		}else {
			isException=1;
			System.out.println("Json名与图片名不匹配,文件名:"+file.getName());
		}
		return isException;
	}
	/*public boolean getJson(String jsonString,String filterKey) {
		HashMap<String,String> objson = JSONObject.parseObject(jsonString, new TypeReference<HashMap<String,String>>(){});
		for(Entry<String, String> entry:objson.entrySet()) {
			if(!"".equals(entry.getKey())&&entry.getKey()!=null) {
				if(filterKey.equals(entry.getKey())) {
					return true;
				}
				if(entry.getValue().indexOf("{")>0) {
					this.getJson(entry.getValue(),filterKey);
				}else {
					
				}
				
			}
			
		}
		return false;
	}*/

}
