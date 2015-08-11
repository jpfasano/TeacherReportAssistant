import java.util.ArrayList;


//

public class Templates extends ArrayList<TemplateCategory> {

	public Templates() {
		super();
	}
	
	public int maxTemplateCategorySize(){
		int retVal=0;
		for (TemplateCategory tc : this) {
			if(tc.getTemplates().size()>retVal) 
				retVal= tc.getTemplates().size();
		}
		return retVal;
	}


}
