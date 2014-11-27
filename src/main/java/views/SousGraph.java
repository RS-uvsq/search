package views;


import java.awt.Component;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JFrame;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.swingViewer.View;
import org.graphstream.ui.swingViewer.Viewer;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.util.FileManager;
import edu.uci.ics.jung.graph.Graph;




public class SousGraph {
   private  org.graphstream.graph.Graph graph;
   private  org.graphstream.graph.Graph graphfinal;
   private List<Node> mode;
   List<String>res;
   Graph<RDFNode, Statement> g;
   JFrame fenetre;
   List<RDFNode> Ressource;
   String lien;
   Shortway dijkstra;
   
   
   
   public SousGraph(String mot,List<String>Resource,org.graphstream.graph.Graph graphfinal, JFrame fenetre)
   {
      this.lien=mot;
      this.res=Resource;
      this.graphfinal=graphfinal;
      this.fenetre=fenetre;
   }
   
   
   
   public void execute()
   {
      
      
      Model model = FileManager.get().loadModel(lien);
      Graph<RDFNode, Statement> g = new JenaJungGraph(model);
      
      graph = new SingleGraph("Le plus court chemin");
      
      
      mode=new ArrayList<Node>();
      init(g);
      dijkstra = new Shortway(g);
      List<RDFNode>Resource=new ArrayList<RDFNode>();
      for(String vf: res)
      {
         Resource.add(dijkstra.getNode(vf));
      }
      
      int i,j;
      for(i=0;i<Resource.size();i++)
      {
         
         for(j=0;j<Resource.size();j++)
         {
            
            chemin(Resource.get(i),Resource.get(j),graph,g);
         }
      }
      Viewer viewer = new Viewer(graphfinal, Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
      viewer.enableAutoLayout();
      View view = viewer.addDefaultView(false);
      
      fenetre.add((Component) view);
      fenetre.setVisible(true);
      
   }
   
   
   
   
   
   
   public void chemin(RDFNode a,RDFNode b,org.graphstream.graph.Graph graph1, Graph<RDFNode, Statement> graph)
   {
      
      try
      {
         dijkstra = new Shortway(graph);
         dijkstra.execute(a);
         LinkedList<RDFNode> path = dijkstra.getPath(b);
         System.out.println(path.size());
         
         
         int i;
         
         if(path.isEmpty())
         {}
         else
         {
            
            
            for(i=0;i<path.size();i++)
            {
               
               if(i!=path.size()-1 && i+1<path.size())
               {
                  RDFNode z=dijkstra.getNode(path.get(i).toString());
                  RDFNode z1=dijkstra.getNode(path.get(i+1).toString());
                  
                  Statement e1=graph.findEdge(z, z1);
                  
                  
                  if(e1.isReified()){}
                  else
                  {
                     System.out.println("taille:"+path.size());
                     CreateNoeud(path.get(i).toString(),graphfinal);
                     CreateNoeud(path.get(i+1).toString(),graphfinal);
                     Edge ab=graph1.addEdge(path.get(i).toString()+path.get(i+1).toString(), path.get(i).toString(),path.get(i+1).toString());
                     Edge ab1=graphfinal.addEdge(path.get(i).toString()+path.get(i+1).toString(), path.get(i).toString(),path.get(i+1).toString());
                     
                     
                     ab.setAttribute("ui.label",e1.getPredicate().toString());
                     ab1.setAttribute("ui.label",e1.getPredicate().toString());
                     
                     
                     
                  }
               }
               
            }
         }
      }
      catch(Exception e)
      {
         
         
      }
      
   }
   
   
   
   
   public void init(Graph<RDFNode, Statement> g)
   {
      Collection<Statement> h=g.getEdges();
      Iterator<Statement> i=h.iterator();
      int j=0;
      graph= new SingleGraph("Le plus court chemin");
      while(i.hasNext())
      {
         Statement s=i.next();
         
         RDFNode source= g.getSource(s);
         RDFNode destination= g.getDest(s);
         CreateNoeud(source.toString(),graph);
         CreateNoeud(destination.toString(),graph);
        // CreateEdge(j+"",source.toString(),destination.toString(),s.getPredicate().toString());
         j++;
         
      }
      graphfinal.addAttribute("ui.quality");
      graphfinal.addAttribute("ui.antialias");
      graphfinal.addAttribute("ui.stylesheet",
      
      " graph {fill-color: #D8BFD8;}"+
      "edge{shape: L-square-line; fill-color:white ;arrow-size: 5px, 10px;size:2px;}"+
      "node {text-color:yellow;shadow-mode:plain;size: 20px, 21px; shape: box;fill-color:red;stroke-mode: plain;stroke-color: yellow;}"
      
      );
      graph.addAttribute("ui.quality");
      graph.addAttribute("ui.antialias");
      graph.addAttribute("ui.stylesheet",
      
      " graph {fill-color: #D8BFD8;}"+
      "edge{shape: L-square-line; fill-color:white ;arrow-size: 5px, 10px;size:2px;}"+
      "node {text-color:yellow;shadow-mode:plain;size: 20px, 21px; shape: box;fill-color:red;stroke-mode: plain;stroke-color: yellow;}"
      
      );
   }
   public void CreateEdge(String nom,String source,String Dest,String name)
   {
      
      Edge ab=graph.addEdge(nom,source,Dest);
      ab.setAttribute("ui.label",name);
   }
   public void CreateNoeud(String c,org.graphstream.graph.Graph f)
   {
      try
      {
         Node e=f.getNode(c);
         String s=e.toString();
         
      }
      catch(Exception e){
         
         Node a=f.addNode(c);
         a.setAttribute("ui.label",c);
         
      }
   }
   
   
   
}
