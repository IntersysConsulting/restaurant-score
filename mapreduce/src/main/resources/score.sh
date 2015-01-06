
function setup() {

	export JOB_JAR=/home/chrisgerken/workspaces/Helios/hadoop/cassandra.example/target/card.tips-0.0.1-SNAPSHOT-jar-with-dependencies.jar;

}

function run_workflow () {

	/home/chrisgerken/Downloads/hadoop-0.20.2/bin/hadoop jar $JOB_JAR com.intersys.score.Workflow;

}

function run_entire_flow () {

	setup;

	run_workflow;

}
