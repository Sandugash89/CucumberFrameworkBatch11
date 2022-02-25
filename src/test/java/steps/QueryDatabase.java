package steps;

import io.cucumber.java.en.When;
import utils.DBUtils;
import utils.GlobalVariables;

public class QueryDatabase {
    @When("query the information from backend")
    public void query_the_information_from_backend() {

        String query="select * from hs_hr_employees where employee_id='"+ GlobalVariables.emp_id+"'";
        GlobalVariables.tableData=DBUtils.getTableData(query);

    }
}
