package com.thizgroup.drools.product;


import com.thizgroup.drools.model.Product;
import java.util.Collection;
import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.KnowledgeBase;
import org.kie.internal.KnowledgeBaseFactory;
import org.kie.internal.builder.KnowledgeBuilder;
import org.kie.internal.builder.KnowledgeBuilderFactory;
import org.kie.internal.definition.KnowledgePackage;
import org.kie.internal.io.ResourceFactory;
import org.kie.internal.runtime.StatefulKnowledgeSession;

public class ProductDroolsTest {

  @Test
  public void oldExecuteDrools(){
    KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
    // 添加规则文件。
    kbuilder.add(ResourceFactory.newClassPathResource("com/rules/Rules.drl", this.getClass()), ResourceType.DRL);
    // 校验规则是否有问题。
    if (kbuilder.hasErrors()) {
      System.out.println(kbuilder.getErrors().toArray());
    }

    // 获取规则中的包
    Collection<KnowledgePackage> pkgs = kbuilder.getKnowledgePackages();
    // 添加规则中的包到规则base
    KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
    // 将KnowledgePackage 集合添加到KnowledgeBase中
    kbase.addKnowledgePackages(pkgs);

    // 获取KnowledgeSession
    StatefulKnowledgeSession ksession = kbase.newStatefulKnowledgeSession();

    Product product = new Product();
    product.setType(Product.GOLD);

    // 引入匹配请求
    ksession.insert(product);
    // 匹配所有规则
    ksession.fireAllRules();
    // 销毁
    ksession.dispose();

    System.out.println("The discount for the product " + product.getType() + " is " + product.getDiscount() + "%");

  }

  @Test
  public void testRules() {
    // 构建KisServices
    KieServices ks = KieServices.Factory.get();
    // 根据Classpath:MATE-INF目录加载kmodule.xml文件
    KieContainer kieContainer = ks.getKieClasspathContainer();
    // 获取kmodule.xml中配置的名称为ksession-rule的session，默认为有状态的。
    KieSession kSession = kieContainer.newKieSession("ksession-rule");

    Product product = new Product();
    product.setType(Product.GOLD);

    // 添加product FACT对象
    kSession.insert(product);
    // 进行规则判断
    int count = kSession.fireAllRules();
    System.out.println("命中了" + count + "条规则!");
    System.out.println("商品" + product.getType() + "的商品折扣为" + product.getDiscount() + "%");
  }

}
