package benchmark.rovers;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import structure.intf.QEA;

public class OutputDoWork extends DoWork<String> {

	private String traces = "traces";
	
	@Override
	public void run_with_spec(String spec, String name, int[] args) {	
		try{
			out = new PrintWriter(new BufferedWriter(new FileWriter(traces+"/"+name+".trace")));
		}catch(IOException e){
			System.err.println("Oh dear");
			System.exit(0);
		}
		dowork(name, args);
	}

	private PrintWriter out;

	private String id(Object o){
		return System.identityHashCode(o)+"";
	}
	
	private void output(String name, String... args){
		//CSV
		out.print(name);
		for(int i=0;i<args.length;i++){
			out.print(",arg"+i+"="+args[i]);
		}
		out.println();
		out.flush();
	}
	
	@Override
	public void command(int x) {
		output("command",x+"");
	}

	@Override
	public void request(Object o) {
		output("request",id(o));
	}

	@Override
	public void grant(Object o) {
		output("grant",id(o));
	}

	@Override
	public void deny(Object o) {
		output("deny",id(o));
	}

	@Override
	public void rescind(Object o) {
		output("rescind",id(o));
	}

	@Override
	public void cancel(Object o) {
		output("cancel",id(o));
	}

	@Override
	public void succeed(Object o) {
		output("succeed",id(o));
	}

	@Override
	public void command(Object o) {
		output("command",id(o));
	}

	@Override
	public void set_ack_timeout(int x) {
		output("set_ack_timeout",x+"");
	}

	@Override
	public void command(Object a, Object b, Object c, int d) {
		output("command",id(a),id(b),id(c),""+d);
	}

	@Override
	public void ack(Object o, int x) {
		output("ack",id(o),x+"");
	}

	@Override
	public void grant(Object a, Object b) {
		output("grant",id(a),id(b));
	}

	@Override
	public void cancel(Object a, Object b) {
		output("cancel",id(a),id(b));
	}

	@Override
	public void schedule(Object a, Object b) {
		output("schedule",id(a),id(b));
	}

	@Override
	public void finish(Object o) {
		output("finish",id(o));
	}

	@Override
	public void conflict(Object a, Object b) {
		output("conflict",id(a),id(b));
	}

	@Override
	public void ping(Object a, Object b) {
		output("ping",id(a),id(b));
	}

	@Override
	public void ack(Object a, Object b) {
		output("ack",id(a),id(b));
	}

	@Override
	public void ack(Object a, Object b, int c) {
		output("ack",id(a),id(b),c+"");
	}

	@Override
	public void send(Object a, Object b, int c) {
		output(" send",id(a),id(b),c+"");
	}

}
