package CorbaModule;

/**
* CorbaModule/ProjectHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from CorbaModule.idl
* Tuesday, October 23, 2018 at 9:46:16 PM Eastern Daylight Time
*/

public final class ProjectHolder implements org.omg.CORBA.portable.Streamable
{
  public CorbaModule.Project value = null;

  public ProjectHolder ()
  {
  }

  public ProjectHolder (CorbaModule.Project initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = CorbaModule.ProjectHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    CorbaModule.ProjectHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return CorbaModule.ProjectHelper.type ();
  }

}