import java.io.*;
import java.util.*;
import java.security.*;
import java.lang.Thread.UncaughtExceptionHandler;
public class CompilerServer{
		static class MySecurityManager extends SecurityManager {
				@Override public void checkExit(int status) {
				throw new SecurityException();
				}

				@Override public void checkPermission(Permission perm) {
				// Allow other activities by default
				}
		}
		public static void main(String[] args){
				final File a = new File(args[0]);
				System.setSecurityManager(new MySecurityManager());
				Thread watcher = new Thread(){
						public void run(){
								long b = a.lastModified();
								while(true){
										long d = a.lastModified();
										if(d != b){System.out.println("Changed");
												recompile(a);
												b = d;
										}

										try{
												Thread.sleep(1000);}catch(Exception o){
														return;
												}
								}
						}
				};


				watcher.setUncaughtExceptionHandler(new UncaughtExceptionHandler() {
						public void uncaughtException(Thread t, Throwable e) {
								// log it, dump it to the console, or ...
								e.printStackTrace();
						}
				});
				watcher.start();
				System.out.println("Watching "+args[0]);
		}
		public static void recompile(File args){
				System.out.println("Recompiling");
				try{
						//ArrayList<String> i = new ArrayList<String>();

						Scanner scanner = new Scanner(args);
						String content = scanner.nextLine();
						System.out.println("arguments: "+content);
						System.out.println(content.split(" ").length);

						scanner.close();
						org.jetbrains.kotlin.cli.jvm.K2JVMCompiler.main(content.split(" "));
				}
				catch(Exception e){
						System.out.println(e.toString());
				}
				System.out.println("Done");}
}
