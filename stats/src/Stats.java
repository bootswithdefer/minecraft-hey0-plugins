import java.io.*;
import java.util.*;
import java.util.logging.MemoryHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.LogRecord;
import java.util.logging.Formatter;
import java.util.Date.*;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.Arrays;





public class Stats extends Plugin {

	public PropertiesFile propertiesFile;
	
	public Date date;
	public Date oldDate;
	public Calendar cal;
	public String[] lineArray;
	
	public SimpleDateFormat dateFormatLogEntry = new SimpleDateFormat("yyMMdd-HH.mm.ss");
	public SimpleDateFormat dateFormatLogFile = new SimpleDateFormat("yyMMdd");

    protected static final Logger log = Logger.getLogger("Minecraft");
	protected static final Logger statLogger = Logger.getLogger("Stats");
    private final String newLine = System.getProperty("line.separator");
    private final String fileSep = System.getProperty("file.separator");
	
	
	private int maxLogLines;
	private String logDir;
	
    public Stats() {
		propertiesFile = new PropertiesFile("stats.properties");
		statLogger.setUseParentHandlers(false);
	}
	
	public boolean load() {
        try {
            File f = new File("stats.properties");
            if (f.exists())
			{
                propertiesFile.load();
			}
            else
			{
                f.createNewFile();
			}
        } catch (Exception e) {
            log.log(Level.SEVERE, "[Stats] : Exception while creating Stats properties file.", e);
        }
        
		
		
        logDir = propertiesFile.getString("logdir", "stats");
        maxLogLines = propertiesFile.getInt("maxLogLines", 200);
		
        // Check for stats directory structure
		try {
			File logDirectory = new File(logDir + fileSep + "players");
			if (!logDirectory.exists())
				logDirectory.mkdirs();
		} catch (Exception e) {
			log.log(Level.SEVERE, "[Stats] : Exception while creating Stats directory structure.", e);
		}
			
		for  (Player p : etc.getServer().getPlayerList() ) {
			String playerDir = logDir + fileSep + "players" + fileSep + p.getName().toLowerCase();
			try {
				File logDirectory = new File(playerDir);
				if (!logDirectory.exists())
					logDirectory.mkdirs();
			} catch (Exception e) {
				log.log(Level.SEVERE, "[Stats] : Exception while creating Stats directory structure.", e);
			}
			
			// Create logger
			createLogger(p.getName().toLowerCase(), playerDir, "movements");
			createLogger(p.getName().toLowerCase(), playerDir, "actions");
			
		}	
		
        try {
            propertiesFile.save();
        } catch (Exception e) {
                log.log(Level.SEVERE, "[Stats] : Exception while saving Stats properties file.", e);
        }
        
        return true;
		
		
    }
    
    public void enable() {
        if (load())
		{
            log.info("[Stats] Mod Enabled.");
					}	
        else
		{
            log.info("[Stats] Error while loading.");
			}
    }
    
    
    public void disable() {
        log.info("[Stats] Mod Disabled.");
		
    }

    public String onLoginChecks(String user) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void onLogin(Player player) {
		String date = dateFormatLogEntry.format(new java.util.Date());
		String location = Double.toString(player.getX()) + "," + Double.toString(player.getY()) + "," + Double.toString(player.getZ());
		
		String message = date;
		message += " ";
		message += player.getName().toLowerCase();
		message += " ";
		message += location;
		message += " ";
		message += "LOGIN";
		message += "\n";
		
		Object[] params = new Object[2]();
		params[0] = "connections";
		params[1] = player.getName().toLowerCase();
					
		// Log to movements.log
		statLogger.log(Level.INFO, message, params);
    }
	
	public void onDisconnect(Player player) { 
		String date = dateFormatLogEntry.format(new java.util.Date());
		String location = Double.toString(player.getX()) + "," + Double.toString(player.getY()) + "," + Double.toString(player.getZ());
		
		String message = date;
		message += " ";
		message += player.getName().toLowerCase();
		message += " ";
		message += location;
		message += " ";
		message += "DISCONNECT";
		message += "\n";
		
		Object[] params = new Object[2]();
		params[0] = "connections";
		params[1] = player.getName().toLowerCase();
					
		// Log to movements.log
		statLogger.log(Level.INFO, message, params);
	
	}

    public boolean onChat(Player player, String message) {
        String date = dateFormatLogEntry.format(new java.util.Date());
		String location = Double.toString(player.getX()) + "," + Double.toString(player.getY()) + "," + Double.toString(player.getZ());
		String messageLength = Integer.toString(message.length);
		
		String message = date;
		message += " ";
		message += player.getName().toLowerCase();
		message += " ";
		message += location;
		message += " ";
		message += "CHAT";
		message += " ";
		message += messageLength;
		message += "\n";
		
		Object[] params = new Object[2]();
		params[0] = "actions";
		params[1] = player.getName().toLowerCase();
					
		// Log to movements.log
		statLogger.log(Level.INFO, message, params);
    }

    public boolean onCommand(Player player, String[] split) {
        String date = dateFormatLogEntry.format(new java.util.Date());
		String location = Double.toString(player.getX()) + "," + Double.toString(player.getY()) + "," + Double.toString(player.getZ());
		String command = split[0];
		
		String message = date;
		message += " ";
		message += player.getName().toLowerCase();
		message += " ";
		message += location;
		message += " ";
		message += "COMMAND";
		message += " ";
		message += command;
		message += "\n";
		
		Object[] params = new Object[2]();
		params[0] = "actions";
		params[1] = player.getName().toLowerCase();
					
		// Log to movements.log
		statLogger.log(Level.INFO, message, params);
    }

    public void onBan(Player player, String reason) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void onIpBan(Player player, String reason) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void onKick(Player player, String reason) {
        String date = dateFormatLogEntry.format(new java.util.Date());
		String location = Double.toString(player.getX()) + "," + Double.toString(player.getY()) + "," + Double.toString(player.getZ());
		
		String message = date;
		message += " ";
		message += player.getName().toLowerCase();
		message += " ";
		message += location;
		message += " ";
		message += "LOGIN";
		message += "\n";
		
		Object[] params = new Object[2]();
		params[0] = "connections";
		params[1] = player.getName().toLowerCase();
					
		// Log to movements.log
		statLogger.log(Level.INFO, message, params);
    }
	
    public boolean onBlockCreate(Player player, Block blockPlaced, Block blockClicked, int itemInHand) { 
    	
		String date = dateFormatLogEntry.format(new java.util.Date());
		String location = Double.toString(blockPlaced.getX()) + "," + Double.toString(blockPlaced.getY()) + "," + Double.toString(blockPlaced.getZ());
		String blockPlacedType = Integer.toString(blockPlaced.getType());
		String blockClickedType = Integer.toString(blockClicked.getType());
		String itemInHand = Integer.toString(itemInHand);
		
		
		String message = date;
		message += " ";
		message += player.getName().toLowerCase();
		message += " ";
		message += location;
		message += " ";
		message += "CREATE";
		message += " ";
		message += blockPlacedType
		message += " ";
		message += blockClickedType
		message += " ";
		message += itemInHand
		message += "\n";
		
		Object[] params = new Object[2]();
		params[0] = "actions";
		params[1] = player.getName().toLowerCase();
					
		// Log to movements.log
		statLogger.log(Level.INFO, message, params);
		
		return false;
    }
    public boolean onBlockDestroy(Player player, Block block) { 
    
        String date = dateFormatLogEntry.format(new java.util.Date());
		String location = Double.toString(block).getX()) + "," + Double.toString(block).getY()) + "," + Double.toString(block).getZ());
		String blockType = Integer.toString(block.getType());
		
		
		String message = date;
		message += " ";
		message += player.getName().toLowerCase();
		message += " ";
		message += location;
		message += " ";
		message += "DESTROY";
		message += " ";
		message += blockType
		message += " ";
		message += distance;
		message += "\n";
		
		Object[] params = new Object[2]();
		params[0] = "actions";
		params[1] = player.getName().toLowerCase();
					
		// Log to movements.log
		statLogger.log(Level.INFO, message, params);
		
		return false;
    }

    public void onPlayerMove(Player player, Location from, Location to) {
	
		String date = dateFormatLogEntry.format(new java.util.Date());
		String from = Double.toString(from.x) + "," + Double.toString(from.y) + "," + Double.toString(from.z);
		String to = Double.toString(to.x) + "," + Double.toString(to.y) + "," + Double.toString(to.z);
		String distance = Double.toString( Math.abs(from.x - to.x) + Math.abs(from.y - to.y) + Math.abs(from.z - to.z));
		
		String message = date;
		message += " ";
		message += player.getName().toLowerCase();
		message += " ";
		message += from;
		message += " ";
		message += to;
		message += " ";
		message += distance;
		message += "\n";
		
		Object[] params = new Object[2]();
		params[0] = "movements";
		params[1] = player.getName().toLowerCase();
					
		// Log to movements.log
		statLogger.log(Level.INFO, message, params);
		
		// Add to summary
		//!TODO!
		
	}
		
	
	
	public boolean createLogger(String playerName, String playerDir, String action) {
		try {
			// Create a memory handler with a memory of 200 records
			// and dumps the records into the file my.log when a
			// some abitrary condition occurs
			FileHandler fhandler = new FileHandler(playerDir + fileSep + action + dateFormatLogFile(new java.util.Date()) +".log");
							
			 fhandler.setFormatter(new Formatter() {
			  public String format(LogRecord record) {
				String returnString = "failed to construct log";
				try {
					//log.info("formatting record");
					Object[] params = record.getParameters();
					
					String date = dateFormatLogEntry.format((Date)params[5]);
					String playerName = (String)params[1];
					Location fromLocation = (Location)params[2];
					Location toLocation = (Location)params[3];
					
					String from = Double.toString(fromLocation.x) + "," + Double.toString(fromLocation.y) + "," + Double.toString(fromLocation.z);
					String to = Double.toString(toLocation.x) + "," + Double.toString(toLocation.y) + "," + Double.toString(toLocation.z);
					String distance = Double.toString( Math.abs(fromLocation.x - toLocation.x) + Math.abs(fromLocation.y - toLocation.y) + Math.abs(fromLocation.z - toLocation.z));

					returnString = date + " " + from + " " + to + " " + distance + "\n";
					//log.info("returning: "+ returnString);
				}
				catch (Exception e) {
					//log.info("exception: "+ e);
				}
				return returnString;
				
			  }
			}); 

			StatsHandler mhandler = new StatsHandler(playerName, action, maxLogLines, fhandler);
			
			// Add to the desired logger
			statLogger.addHandler(mhandler);
		} catch (Exception e) {
			log.info("Exception" + e);
		}
	}
	
}

