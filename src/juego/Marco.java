package juego;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

import com.panamahitek.ArduinoException;
import com.panamahitek.PanamaHitek_Arduino;
import com.panamahitek.PanamaHitek_MultiMessage;

import clases.Actor;
import clases.Pared;
import clases.Player;
import clases.Punto;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import clases.Caja;

public class Marco extends JPanel{
	
	private static final long serialVersionUID = 1L;
	
	private final int OFFSET = 50;
	private final int SPACE = 64;
    private final int LEFT_COLLISION = 1;
    private final int RIGHT_COLLISION = 2;
    private final int TOP_COLLISION = 3;
    private final int BOTTOM_COLLISION = 4;
	
	private ArrayList<Pared>paredes;
	private ArrayList<Caja>cajas;
	private ArrayList<Punto> puntos;
	
	private int w = 0;
	private int h = 0;
	private Player jugador;
	private Player jugador2;
	
    private boolean isCompleted = false;
	
	private String nivel
     		= "###################\n"
     		+ "#.   $         *  #\n"
     		+ "#### ##############\n"
     		+ "#  $             .#\n"
     		+ "# #   ### #########\n"
     		+ "# # ##### #########\n"
     		+ "# #.##### #########\n"
     		+ "# ## $ $         .#\n"
     		+ "# @       #########\n"
     		+ "###################\n";
     		
	//-----------------------VARIABLES PARA LOS CONTROLES----------------------------------------------
	private String comparar[] = {"X:0", "X:1023", "Y:0", "Y:1023"};
	private String comparar2[] = {"X2:0", "X2:1023", "Y2:0", "Y2:1023"};
	
	private final String PUERTO = "COM4";
	private final int BITS_S = 9600;
    private final int mensajes = 4;
    
    PanamaHitek_Arduino ar;
    PanamaHitek_MultiMessage men;
    
    private String ejeX, ejeY, ejeX2, ejeY2;
	//-------------------------------------------------------------------------------------------------

	//-----------------METODO CONSTRUCTOR DE LA CLASE---------------------------------------------------
	public Marco() {
		
		iniciaMundo();
		ar = new PanamaHitek_Arduino();
	    men = new PanamaHitek_MultiMessage(mensajes, ar); 
	    
	    try {
			ar.arduinoRX(PUERTO, BITS_S, listener);
		} catch (ArduinoException | SerialPortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//--------------------------------------------------------------------------------------------------
	
	private void iniciaMundo() {
		
		paredes = new ArrayList<>();
		cajas   = new ArrayList<>();
		puntos  = new ArrayList<>();
		
		int x = OFFSET;
		int y = OFFSET;
		
		Pared pared;
		Caja  caja;
		Punto punto;
		
		for (int i = 0; i < nivel.length(); i++) {
			
			char item = nivel.charAt(i);
			
			switch (item) {
			
				case '\n':
					y += SPACE;

					if (this.w < x) {
						this.w = x;
					}

					x = OFFSET;
					break;
			
				case '#':
					pared = new Pared(x, y);
					paredes.add(pared);
					x += SPACE;
					break;
				
				case '@':
					jugador = new Player(x, y);
					x += SPACE;
					break;
					
				case '$':
					caja = new Caja(x, y);
					cajas.add(caja);
					x += SPACE;
					break;
				
				case '.':
					punto = new Punto(x, y);
					puntos.add(punto);
					x += SPACE;
					break;
					
				case ' ':
					x += SPACE;
					break;
					
				case '*':
					jugador2 = new Player(x, y);
					x += SPACE;
					break;
					
				default:
					break;
			}
			h = y;
		}
	}
	
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        construyeMundo(g);    
    }
    
    private void construyeMundo(Graphics g) {
	
    	 g.setColor(new Color(250, 240, 170));
         g.fillRect(0, 0, this.getWidth(), this.getHeight());

         ArrayList<Actor> mundo = new ArrayList<>();

         mundo.addAll(paredes);
         mundo.addAll(puntos);
         mundo.addAll(cajas);
         mundo.add(jugador);
         mundo.add(jugador2);
        
         for (int i = 0; i < mundo.size(); i++) {

             Actor item = mundo.get(i);
             
             if(item instanceof Player || item instanceof Caja)
            	 g.drawImage(item.getImage(), item.x() + 2, item.y() + 2, this);
             else
            	 g.drawImage(item.getImage(), item.x(), item.y(),this);
             
             if(isCompleted) {
            	 g.setColor(Color.RED);
            	 g.setFont(new Font("Serif", Font.BOLD, 40));
                 g.drawString("Completado", 510, 32); 
             }
         }
    }
    
    public int getMarcoWidth() {
    	return this.w;
    }
    
    public int getMarcoHeight() {

    	return this.h;
    }
    
    private boolean checkParedCollision(Actor actor, int tipo) {

    	
    	switch (tipo) {
			case LEFT_COLLISION:
				for (int i = 0; i < paredes.size(); i++) {
					Pared pared = paredes.get(i);
					if (actor.isLeftCollision(pared))
						return true;
			}
			
				return false;
			
			case RIGHT_COLLISION:
				for (int i = 0; i < paredes.size(); i++) {
					Pared pared = paredes.get(i);
					if(actor.isRightCollision(pared))
						return true;
				}
				
				return false;
				
			case TOP_COLLISION:
				for (int i = 0; i < paredes.size(); i++) {
					Pared pared = paredes.get(i);
					if (actor.isTopCollision(pared))
						return true;
			}
			
				return false;
			
			case BOTTOM_COLLISION:
				for (int i = 0; i < paredes.size(); i++) {
					Pared pared = paredes.get(i);
					if(actor.isBottomCollision(pared))
						return true;
				}
				
				return false;

		default:
			break;
		}
    	
    	return false;
    }
    
    private boolean checkobjectCollision(int tipo, Player ju) {
    	switch (tipo) {
    	
			case LEFT_COLLISION:
			
				for (int i = 0; i < cajas.size(); i++) {
				
					Caja ca = cajas.get(i);
				
					if(ju.isLeftCollision(ca)) {
					
						for (int j = 0; j < cajas.size(); j++) {
						
							Caja item = cajas.get(j);
						
							if(!ca.equals(item)) {
							
								if(ca.isLeftCollision(item)) {
								return true;
								}
							}if(checkParedCollision(ca, LEFT_COLLISION)) {
								return true;
							}
						}
						ca.move(-SPACE, 0);
						isComplete();
					}
				}
				return false;

			case RIGHT_COLLISION:
				
				for (int i = 0; i < cajas.size(); i++) {
					
					Caja ca = cajas.get(i);
				
					if(ju.isRightCollision(ca)) {
					
						for (int j = 0; j < cajas.size(); j++) {
						
							Caja item = cajas.get(j);
						
							if(!ca.equals(item)) {
							
								if(ca.isRightCollision(item)) {
								return true;
								}
							}if(checkParedCollision(ca, RIGHT_COLLISION)) {
								return true;
							}
						}
						ca.move(SPACE, 0);
						isComplete();
					}
				}
				return false;
				
			case TOP_COLLISION:
				
				for (int i = 0; i < cajas.size(); i++) {
				
					Caja ca = cajas.get(i);
				
					if(ju.isTopCollision(ca)) {
					
						for (int j = 0; j < cajas.size(); j++) {
						
							Caja item = cajas.get(j);
						
							if(!ca.equals(item)) {
							
								if(ca.isTopCollision(item)) {
								return true;
								}
							}if(checkParedCollision(ca, TOP_COLLISION)) {
								return true;
							}
						}
						ca.move(0, -SPACE);
						isComplete();
					}
				}
				return false;

			case BOTTOM_COLLISION:
				
				for (int i = 0; i < cajas.size(); i++) {
				
					Caja ca = cajas.get(i);
				
					if(ju.isBottomCollision(ca)) {
					
						for (int j = 0; j < cajas.size(); j++) {
						
							Caja item = cajas.get(j);
						
							if(!ca.equals(item)) {
							
								if(ca.isBottomCollision(item)) {
								return true;
								}
							}if(checkParedCollision(ca, BOTTOM_COLLISION)) {
								return true;
							}
						}
						ca.move(0, SPACE);
						isComplete();
					}
				}
				return false;

				
			default:
				break;
		}
		return false;
    }
    
    public void isComplete() {
    	
    	int objetos  = cajas.size();
    	int finished = 0;
    	
    	for (int i = 0; i < objetos; i++) {
			
    		Caja caj = cajas.get(i);
    		
    		for (int j = 0; j < objetos; j++) {
				
    			Punto punto = puntos.get(j);
    			
    			if(caj.x() == punto.x() && caj.y() == punto.y()) {
    				
    				finished += 1; 
    			}
			}
		}
    	if(finished == objetos) {
    		
    		isCompleted = true;
    	}
    }
    
    //-------------------------trabajando con arduino---------------------------
    SerialPortEventListener listener = new SerialPortEventListener() {

		@Override
		public void serialEvent(SerialPortEvent arg0) {
			
				try {
					if(men.dataReceptionCompleted()) {
						 ejeX  = men.getMessage(0);
					     ejeY  = men.getMessage(1);
					     ejeX2 = men.getMessage(2);
					     ejeY2 = men.getMessage(3);

					     //--------------CONTROL 1 ---------------------------------
					     if(ejeX.equals(comparar[0])) {
					    	 if(!checkParedCollision(jugador, LEFT_COLLISION)) {
					    		 if(!checkobjectCollision(LEFT_COLLISION, jugador))
					    			 jugador.move(-SPACE, 0);
					    	 }
					    		 
					    	 
					     }else if(ejeX.equals(comparar[1])) {
					    	 if(!checkParedCollision(jugador, RIGHT_COLLISION)) {
					    		 if(!checkobjectCollision(RIGHT_COLLISION, jugador))
					    		 	jugador.move(SPACE, 0);
					    	 }	 
					    	 
					     }else if(ejeY.equals(comparar[2])) {
					    	 if(!checkParedCollision(jugador, TOP_COLLISION)) {
					    		 if(!checkobjectCollision(TOP_COLLISION, jugador))
					    		 	jugador.move(0, -SPACE);
					    	 }
					    	 
					     }else if(ejeY.equals(comparar[3])) {
					    	 if(!checkParedCollision(jugador, BOTTOM_COLLISION)) {
					    		 if(!checkobjectCollision(BOTTOM_COLLISION, jugador))
					    		 	jugador.move(0, SPACE);
					    	 }	 
					     }//-----------------------------------------------------------
					     
					     
					     //---------------------CONTROL 2-----------------------------
					     if(ejeX2.equals(comparar2[0])) {
					    	 if(!checkParedCollision(jugador2, LEFT_COLLISION)) {
					    		 if(!checkobjectCollision(LEFT_COLLISION, jugador2))
					    			 jugador2.move(-SPACE, 0);
					    	 }
					    		 
					    	 
					     }else if(ejeX2.equals(comparar2[1])) {
					    	 if(!checkParedCollision(jugador2, RIGHT_COLLISION)) {
					    		 if(!checkobjectCollision(RIGHT_COLLISION, jugador2))
					    			 jugador2.move(SPACE, 0);
					    	 }
					    	 
					     }else if(ejeY2.equals(comparar2[2])) {
					    	 if(!checkParedCollision(jugador2, TOP_COLLISION)) {
					    		 if(!checkobjectCollision(TOP_COLLISION, jugador2))
					    		 	jugador2.move(0, -SPACE);
					    	 }
					    	 
					     }else if(ejeY2.equals(comparar2[3])) {
					    	 if(!checkParedCollision(jugador2, BOTTOM_COLLISION)) {
					    		 if(!checkobjectCollision(BOTTOM_COLLISION, jugador2))
					    		 	jugador2.move(0, SPACE);
					    	 }	 
					     }
					     
					     repaint();
					     men.flushBuffer();
					
}
				} catch (ArduinoException | SerialPortException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
    };
    //---------------------------------------------------------------------------
    
}
