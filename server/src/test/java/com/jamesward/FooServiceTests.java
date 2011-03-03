package com.jamesward;

import com.jamesward.Foo;
import com.jamesward.FooService;

import org.junit.Test;
import static org.junit.Assert.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import javax.validation.ConstraintViolationException;
import java.util.List;

@ContextConfiguration(value = "classpath:applicationContext.xml")
public class FooServiceTests extends AbstractTransactionalJUnit4SpringContextTests
{

  @Autowired
  private FooService fooService;

  @Test
  public void create()
  {
    Foo foo = new Foo();
    foo.name = "bar";

    fooService.create(foo);

    assertNotNull(foo.id);
  }

  @Test
  public void getAll()
  {
    Foo foo1 = new Foo();
    foo1.name = "bar1";
    fooService.create(foo1);

    Foo foo2 = new Foo();
    foo2.name = "bar2";
    fooService.create(foo2);

    List<Foo> foos = fooService.getAll();
    assertEquals(2, foos.size());
  }

  @Test(expected=ConstraintViolationException.class)
  public void createFooWithInvalidName()
  {
    Foo foo = new Foo();
    foo.name = "a";

    fooService.create(foo);
  }

  @Test
  public void updateFoo()
  {
    Foo foo = new Foo();
    foo.name = "bar";

    fooService.create(foo);

    foo.name = "foo";

    assertEquals(fooService.getFooById(foo.id).name, foo.name);
  }

  @Test(expected=ConstraintViolationException.class)
  public void updateFooWithInvalidName()
  {
    Foo foo = new Foo();
    foo.name = "bar";

    fooService.create(foo);

    foo.name = "a";

    fooService.update(foo);
  }

}