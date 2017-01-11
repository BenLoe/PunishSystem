package org.Prison.Punish;

public class MinuteCal {

	public static int calMuteTime(String uuid, int level){
		int priors = 0;
		if (Files.getDataFile().contains("Log." + uuid + ".Chat.level" + level)){
			priors = Files.getDataFile().getInt("Log." + uuid + ".Chat.level" + level);
		}
		if (level == 1){
			if (priors == 0){
				return 20;
			}
			if (priors == 1){
				return 60;
			}
			if (priors == 2){
				return 240;
			}
			if (priors > 2){
				return 1440;
			}
		}
		if (level == 2){
			if (priors == 0){
				return 300;
			}
			if (priors == 1){
				return 1440;
			}
			if (priors == 2){
				return 4320;
			}
			if (priors > 2){
				return 10080;
			}
		}
		if (level == 3){
			return 43200;
		}
		return 1;
	}
	
	public static int calGenTime(String uuid, int level){
		int priors = 0;
		if (Files.getDataFile().contains("Log." + uuid + ".Gen.level" + level)){
			priors = Files.getDataFile().getInt("Log." + uuid + ".Gen.level" + level);
		}
		if (level == 1){
			if (priors == 0){
				return 120;
			}
			if (priors == 1){
				return 300;
			}
			if (priors == 2){
				return 1440;
			}
			if (priors > 2){
				return 4320;
			}
		}
		if (level == 2){
			return -1;
		}
		return 1;
	}
	
	public static int calHackTime(String uuid, int level){
		int priors = 0;
		if (Files.getDataFile().contains("Log." + uuid + ".Hack.level" + level)){
			priors = Files.getDataFile().getInt("Log." + uuid + ".Hack.level" + level);
		}
		if (level == 1){
			if (priors == 0){
				return 300;
			}
			if (priors == 1){
				return 1440;
			}
			if (priors == 2){
				return 7200;
			}
			if (priors > 2){
				return 10080;
			}
		}
		if (level == 2){
			if (priors == 0){
				return 7200;
			}else{
				return -1;
			}
		}
		return 1;
	}
	
}
