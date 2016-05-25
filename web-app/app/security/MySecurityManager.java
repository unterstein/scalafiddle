package security;

import eval.print.OwnPrintStreamThread;

import javax.management.MBeanTrustPermission;
import java.io.FilePermission;
import java.lang.reflect.ReflectPermission;
import java.security.Permission;
import java.util.PropertyPermission;

public class MySecurityManager extends SecurityManager {

  private static boolean doCheck = false;

  public static void enableCheck() {
    doCheck = true;
  }

  @Override
  public void checkAccess(Thread t) {

  }

  @Override
  public void checkAccess(ThreadGroup g) {

  }

  @Override
  public void checkPermission(Permission perm, Object context) {
      this.checkPermission(perm);
  }

  @Override
  public void checkPermission(Permission perm) {
    if (doCheck == true && Thread.currentThread() instanceof OwnPrintStreamThread) {
      final OwnPrintStreamThread thread = (OwnPrintStreamThread) Thread.currentThread();
      // checks from ScalaKataSecurityPolicy
      if (thread.isExecuting()) {
        if (perm instanceof FilePermission) {
          FilePermission filePermission = (FilePermission) perm;
          // scriptPermissions.add(new FilePermission("-", "read")) // all read
          if ("read".equals(filePermission.getActions()) && filePermission.getName().endsWith(".jar")) {
            return;
          }
        }
        if (perm instanceof RuntimePermission) {
          RuntimePermission runtimePermission = (RuntimePermission) perm;
          // scriptPermissions.add(new RuntimePermission("accessDeclaredMembers")) // reflexion
          if ("accessDeclaredMembers".equals(runtimePermission.getName())) {
            return;
          }
          // do not allow !!
          // scriptPermissions.add(new RuntimePermission("getenv.*"))
          /*if (runtimePermission.getName().startsWith("getenv.")) {
            return;
          } */
          // scriptPermissions.add(new RuntimePermission("modifyThread"))
          if ("modifyThread".equals(runtimePermission.getName())) {
            return;
          }
        }
        if (perm instanceof ReflectPermission) {
          ReflectPermission reflectPermission = (ReflectPermission) perm;
          // scriptPermissions.add(new ReflectPermission("suppressAccessChecks"))
          if ("suppressAccessChecks".equals(reflectPermission.getName())) {
            return;
          }
        }
        if (perm instanceof PropertyPermission) {
          // scriptPermissions.add(new PropertyPermission("*", "read"))
          PropertyPermission propertyPermission = (PropertyPermission) perm;
          if ("read".equals(propertyPermission.getActions())) {
            return;
          }
        }
        if (perm instanceof MBeanTrustPermission) {
          return;
        }
        super.checkPermission(perm);
      }
    }
  }
}
