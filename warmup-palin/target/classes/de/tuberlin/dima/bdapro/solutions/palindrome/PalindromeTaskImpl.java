package de.tuberlin.dima.bdapro.solutions.palindrome;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.util.Collector;

public class PalindromeTaskImpl implements PalindromeTask {

	@Override
	public Set<String> solve(String inputFile) {

		//******************************
		//*Implement your solution here*
		ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();



		DataSet<String> texts = env.readTextFile(inputFile);
		/*DataSet<String> texts = env.fromElements(
				"abc cba 33\n" +
						" abc abc cba cba \n" +
						"4 qwer fg gf rewq 4\n" +
						"a qder fg gf redq a\n" +
						"abcde abcde edcba edcba 33  "
		);*/
		DataSet<String> out =texts.flatMap(new FlatMapFunction<String, String> () {

			public void flatMap(String value, Collector<String> out) {
				for (String token : value.split("\n")) {
					out.collect(token);
				}


		}}).map(new MapFunction<String, Tuple2<String,String>> (){
			public Tuple2<String,String> map(String in){
				return new Tuple2<String,String>(in,
						in.replace(" ",""));
			}
		}).filter(new FilterFunction<Tuple2<String, String>>() {
			@Override
			public boolean filter(Tuple2<String, String> in) throws Exception {
				return in.f1.equals( new StringBuilder(in.f1).reverse().toString());
			}
		}).map(new MapFunction<Tuple2<String,String>, Tuple2<String,Integer>> (){
			public Tuple2<String,Integer> map(Tuple2<String,String> in){
				return new Tuple2<String,Integer>(in.f0,
						in.f1.length());
			}
		}).reduce(new ReduceFunction<Tuple2<String, Integer>>() {
			@Override
			public Tuple2<String, Integer> reduce
					(Tuple2<String, Integer> s1, Tuple2<String, Integer> s2)
					throws Exception {
				if (s1.f1>s2.f1){
					return s1;
				}else if(s1.f1<s2.f1){
					return s2;
				}else
					return new Tuple2<String, Integer>
							(s1.f0+"\n"+s2.f0, s1.f1);

			}
		}).flatMap(new FlatMapFunction<Tuple2<String,Integer>, String>() {

			public void flatMap(Tuple2<String,Integer> value, Collector<String> out) {
				for (String token : value.f0.split("\n")) {
					out.collect(token);
				}


			}
		});

		Set<String> resSet = null;
		try {
			resSet = new HashSet<String>(out.collect());
		} catch (Exception e) {
			e.printStackTrace();
		}

		//******************************
		
		return resSet;
	}

}
