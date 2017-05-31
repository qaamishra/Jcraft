# Jcraft

Use this sample programs to connect to your Linux machine ,trigger the command and get back he output.
We can also use Jcraft to connect to windows machine ,provided a ssh server is up and running.
Download one from : http://www.freesshd.com/?ctt=download

These are functional program but requires a host,uname,pwd as input and has to be connected with a main program.

Important Read
https://epaul.github.io/jsch-documentation/simple.javadoc/index.html?com/jcraft/jsch/Session.html
public Channel openChannel(String type) throws JSchException
Opens a new channel of some type over this connection.
Parameters:
type - a string identifying the channel type. For now, the available types are these:
shell - ChannelShell
exec - ChannelExec
direct-tcpip - ChannelDirectTCPIP
sftp - ChannelSftp
subsystem - ChannelSubsystem
