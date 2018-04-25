package es.ucm.fdi.view;

import java.awt.BorderLayout;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;

import es.ucm.fdi.extra.graphlayout.Dot;
import es.ucm.fdi.extra.graphlayout.Edge;
import es.ucm.fdi.extra.graphlayout.Graph;
import es.ucm.fdi.extra.graphlayout.GraphComponent;
import es.ucm.fdi.extra.graphlayout.Node;
import es.ucm.fdi.model.Junction;
import es.ucm.fdi.model.Road;
import es.ucm.fdi.model.RoadMap;
import es.ucm.fdi.model.Vehicle;

public class SimulatorGraph extends JPanel{
	private GraphComponent graphComp;
	private RoadMap rm;
	
	public SimulatorGraph(RoadMap rm){
		super(new BorderLayout());
		this.rm = rm;
		initGUI();
	}
	
	private void initGUI(){
		graphComp = new GraphComponent();
		add(graphComp);
		generateGraph();
		
	}
	
	private void generateGraph(){
		Graph g = new Graph();
		Map<Junction, Node> js = new HashMap<>();
		for (Junction j : rm.getListaCruces()) {
			Node n = new Node(j.getId());
			js.put(j, n); // <-- para convertir Junction a Node en aristas
			g.addNode(n);
		}
		Map<Road, Edge> rs = new HashMap<>();
		for (Road r : rm.getListaCarreteras()) {
			Edge e = new Edge (r.getId(), js.get(r.getcruceIni()), js.get(r.getcruceFin()), r.getLongitud(), r.getcruceFin().estaVerde(r));
			rs.put(r, e);
			g.addEdge(e);
		}
		for(Vehicle v: rm.getListaVehiculos()){
			rs.get(v.getRoad()).addDot(new Dot(v.getId(), v.getPos()));
		}
		graphComp.setGraph(g);
	}
	
	public void update(RoadMap rm){
		this.rm = rm;
		generateGraph();
		
	}
}