package gc01coursework.users_and_login;

public class Employee extends StaffMember{
	public Employee(String username, String password, String lastLogin) {
		super(username, password, lastLogin);
	}

	@Override
	protected String getUsername() {
		return super.getUsername();
	}

	@Override
	protected String getPassword() {
		return super.getPassword();
	}

	@Override
	protected String getLastLogin() {
		return super.getLastLogin();
	}

	@Override
	protected void setUsername(String username) {
		super.setUsername(username);
	}

	@Override
	protected void setPassword(String password) {
		super.setPassword(password);
	}

	@Override
	protected void setLastLogin(String lastLogin) {
		super.setLastLogin(lastLogin);
	}
}
